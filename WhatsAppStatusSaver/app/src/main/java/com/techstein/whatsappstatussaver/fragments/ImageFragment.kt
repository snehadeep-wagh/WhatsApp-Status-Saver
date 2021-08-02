package com.techstein.whatsappstatussaver.fragments

import android.app.Dialog
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import com.bumptech.glide.Glide
import com.techstein.whatsappstatussaver.R
import com.techstein.whatsappstatussaver.adapters.ImageAdapter
import com.techstein.whatsappstatussaver.adapters.ImageListen
import com.techstein.whatsappstatussaver.constants.MyConstants
import kotlinx.android.synthetic.main.fragment_image.view.*
import java.io.File
import java.io.FileOutputStream
import java.io.OutputStream
import java.util.*
import kotlin.collections.ArrayList


class ImageFragment : Fragment() {
    lateinit var imageList: ArrayList<String>
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_image, container, false)
        val imageListen = object : ImageListen
        {
            override fun onClickSaveButton(path: String) {
                copyImage(path)
                Toast.makeText(activity, "Saved button", Toast.LENGTH_SHORT).show()
            }

            override fun onClickImage(currentImage: String) {
                showImage(currentImage)
            }
        }

        imageList = getImageData()
        view.ImageAdapterId.layoutManager = GridLayoutManager(context!!, 3, GridLayoutManager.VERTICAL, false)
        view.ImageAdapterId.adapter = ImageAdapter(context!!, imageList, imageListen)

        view.ImageSwipeLayoutId.setOnRefreshListener {
            imageList = getImageData()
            view.ImageAdapterId.adapter = ImageAdapter(context!!, imageList, imageListen)
            view.ImageAdapterId.adapter!!.notifyDataSetChanged()
            view.ImageSwipeLayoutId.isRefreshing = false
        }

        return view
    }

    fun getImageData(): ArrayList<String>
    {
        val listOfImages = ArrayList<String>()
        val file: File = MyConstants.STATUS_LOC
        val currentList = file.listFiles()

        if(currentList != null && currentList.isNotEmpty())
        {
            // Sort Array----------------------
            Arrays.sort(currentList)

            for(image in currentList)
            {
                if(image.name.endsWith(".jpg") || image.name.endsWith(".jpeg") || image.name.endsWith(".png"))
                {
                    listOfImages.add(image.absolutePath)
                    Log.i("img", image.name)
                }
            }
        }
        else
        {
            Toast.makeText(activity, "You haven't watched any status", Toast.LENGTH_LONG).show()
        }

        return listOfImages
    }

    private fun showImage(currentImage: String)
    {
        val dialog: Dialog = Dialog(activity!!)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.window!!.setBackgroundDrawable(ColorDrawable(android.graphics.Color.TRANSPARENT))
        val imageView = ImageView(activity)
        Glide.with(context!!).load("file://${currentImage}").into(imageView)
        dialog.addContentView(imageView, LinearLayout.LayoutParams(
            ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT
        ))
        dialog.setCancelable(true)
        dialog.show()
    }

    private fun copyImage(sourcePath: String)
    {
        createFolder()?.let { folder ->
         val file = File(sourcePath)
         val destination = File(folder.absolutePath + File.separator + file.name)
            try {
                file.copyTo(destination, false, DEFAULT_BUFFER_SIZE)
            }
            catch (e:Exception)
            {
                Toast.makeText(activity, "Something went wrong!", Toast.LENGTH_SHORT).show()
            }

        }



    }

    private fun createFolder(): File
    {
        var path: String? = null
        val imageFolder = MyConstants.IMAGE_FILE_LOC
        if(!imageFolder.exists())
        {
            imageFolder.mkdirs()
            Log.i("check", "created")
        }

        return imageFolder
    }

}
