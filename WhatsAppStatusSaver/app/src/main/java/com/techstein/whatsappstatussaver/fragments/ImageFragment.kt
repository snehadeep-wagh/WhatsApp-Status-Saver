package com.techstein.whatsappstatussaver.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import com.techstein.whatsappstatussaver.R
import com.techstein.whatsappstatussaver.adapters.ImageAdapter
import com.techstein.whatsappstatussaver.constants.MyConstants
import kotlinx.android.synthetic.main.fragment_image.*
import kotlinx.android.synthetic.main.fragment_image.view.*
import java.io.File
import java.util.*
import kotlin.collections.ArrayList

class ImageFragment : Fragment() {
    lateinit var imageList: ArrayList<String>
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_image, container, false)
        imageList = getImageData()
        view.ImageAdapterId.layoutManager = GridLayoutManager(context!!, 3, GridLayoutManager.VERTICAL, false)
        view.ImageAdapterId.adapter = ImageAdapter(context!!, imageList)
        return view
    }

    fun getImageData(): ArrayList<String>
    {
        val listOfImages = ArrayList<String>()

        val file: File = MyConstants.STATUS_LOC
        val currentList = file.listFiles()

        Log.i("file", file.toString())
        Log.i("path", file.absolutePath.toString())

        if(currentList != null && currentList.isNotEmpty())
        {
            Arrays.sort(currentList)
            Log.i("loc size", currentList.size.toString())
        }

        if(currentList != null && currentList.isNotEmpty())
        {
            Log.i("loc", "Entered loop")
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

}