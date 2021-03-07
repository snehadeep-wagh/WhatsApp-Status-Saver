package com.techstein.whatsappstatussaver.constants

import android.os.Environment
import java.io.File

class MyConstants {
    companion object
    {
        val STATUS_LOC = File(Environment.getExternalStorageDirectory().absolutePath.toString() + "/WhatsApp/Media/.Statuses/")
        val APP_FILE_LOC = File(Environment.getExternalStorageDirectory().absolutePath.toString() + "WhatsApp Status Saver")
    }
}