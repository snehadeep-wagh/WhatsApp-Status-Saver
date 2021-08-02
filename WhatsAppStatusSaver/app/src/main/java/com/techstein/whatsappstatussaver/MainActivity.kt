package com.techstein.whatsappstatussaver

import android.Manifest
import android.annotation.SuppressLint
import android.app.Dialog
import android.content.SharedPreferences
import android.content.pm.PackageManager
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.app.ActivityCompat
import com.google.android.material.tabs.TabLayoutMediator
import com.techstein.whatsappstatussaver.adapters.ViewPagerAdapter
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {
    private var isDark: Boolean = false
    private lateinit var sharedPrefEditor: SharedPreferences.Editor
    private lateinit var infoView: View
    private lateinit var alertDialog: Dialog
    @SuppressLint("CommitPrefEdits", "InflateParams")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbarId)

        alertDialog = Dialog(this)
        infoView = layoutInflater.inflate(R.layout.fragment_info, null)

        // SharedPreference for Theme-----------------------
        val sharedPref = getSharedPreferences("Setting", 0)
        sharedPrefEditor = sharedPref.edit()
        isDark = sharedPref.getBoolean("DarkMode", false)

        //Change Theme------------------------------------
        if(isDark)
        {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
        }
        else
        {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        }

        //Check permissions ----
        checkPermission()

        //Viewpager Adapter ---------------------------------------
        viewPagerId.adapter = ViewPagerAdapter(this)
        TabLayoutMediator(tabLayoutId, viewPagerId, TabLayoutMediator.TabConfigurationStrategy { tab, position ->
            when(position)
            {
                0 -> tab.text = "Images"
                1 -> tab.text = "Videos"
                else -> tab.text = "Downloads"
            }
        }).attach()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){

            R.id.themeId ->{
                isDark = if(isDark) {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                    sharedPrefEditor.putBoolean("DarkMode", false).apply()
                    false
                } else {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                    sharedPrefEditor.putBoolean("DarkMode", true).apply()
                    true
                }
            }
            R.id.infoId ->
            {
                showInfo(infoView)
            }
        }
        return true
    }

    private fun checkPermission()
    {
        val listOfPermissions = ArrayList<String>()

        fun checkReadPermission() = ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED
        fun checkWritePermission() = ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED

        if(!checkReadPermission())
        {
            listOfPermissions.add(Manifest.permission.READ_EXTERNAL_STORAGE)
        }

        if(!checkWritePermission())
        {
            listOfPermissions.add(Manifest.permission.WRITE_EXTERNAL_STORAGE)
        }

        if(listOfPermissions.isNotEmpty())
        {
            ActivityCompat.requestPermissions(this, listOfPermissions.toTypedArray(), 100)
        }
    }

    private fun showInfo(view: View)
    {
        alertDialog.setContentView(view)
        alertDialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        val but = alertDialog.findViewById<TextView>(R.id.okButtonId)
        but.setOnClickListener {
            alertDialog.dismiss()
        }
        alertDialog.show()
    }

}
