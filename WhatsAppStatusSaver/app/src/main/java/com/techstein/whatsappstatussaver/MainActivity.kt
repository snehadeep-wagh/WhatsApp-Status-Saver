package com.techstein.whatsappstatussaver

import android.Manifest
import android.annotation.SuppressLint
import android.content.SharedPreferences
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.app.ActivityCompat
import com.google.android.material.tabs.TabLayoutMediator
import com.techstein.whatsappstatussaver.adapters.ViewPagerAdapter
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {
    private var isDark: Boolean = false
    private lateinit var sharedPrefEditor: SharedPreferences.Editor

    @SuppressLint("CommitPrefEdits")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setSupportActionBar(toolbarId)

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
            R.id.refreshId ->
            {
                Toast.makeText(applicationContext, "Refreshed", Toast.LENGTH_LONG).show()
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

}
