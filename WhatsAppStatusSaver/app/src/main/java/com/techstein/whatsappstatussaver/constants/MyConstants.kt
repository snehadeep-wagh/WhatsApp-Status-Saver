package com.techstein.whatsappstatussaver.constants

import android.os.Environment
import java.io.File

class MyConstants {
    companion object
    {
        val STATUS_LOC = File(Environment.getExternalStorageDirectory().absolutePath.toString() + "/WhatsApp/Media/.Statuses/")
        val APP_FILE_LOC = File(Environment.getExternalStorageDirectory().absolutePath.toString() + "/WhatsApp Status Saver/")
        val VIDEO_FILE_LOC = File(Environment.getExternalStorageDirectory().absolutePath.toString() + "/WhatsApp Status Saver/Videos/")
        val IMAGE_FILE_LOC = File(Environment.getExternalStorageDirectory().absolutePath.toString() + "/WhatsApp Status Saver/Images/")
        const val VIDEO_LOC = "/WhatsApp Status Saver/Videos/"
        const val IMAGE_LOC = "/WhatsApp Status Saver/Images/"
        const val WHATSAPP_LOC = "/WhatsApp/Media/.Statuses/"
    }
}