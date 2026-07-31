package com.pdfpodcaster.android

import android.app.Application
import dagger.hilt.android.HiltAndroidApp
import android.util.Log
import java.io.File
import java.io.FileWriter
import java.io.PrintWriter
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@HiltAndroidApp
class PdfPodcasterApp : Application() {
    override fun onCreate() {
        super.onCreate()
        
        val defaultHandler = Thread.getDefaultUncaughtExceptionHandler()
        Thread.setDefaultUncaughtExceptionHandler { thread, throwable ->
            try {
                val dir = getExternalFilesDir(null)
                if (dir != null) {
                    val file = File(dir, "crash_log.txt")
                    val writer = FileWriter(file, true)
                    val printWriter = PrintWriter(writer)
                    val dateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.US)
                    printWriter.println("--- CRASH REPORT ${dateFormat.format(Date())} ---")
                    throwable.printStackTrace(printWriter)
                    printWriter.println("----------------------------------------\n")
                    printWriter.close()
                    Log.e("PdfPodcasterApp", "Crash saved to ${file.absolutePath}")
                }
            } catch (e: Exception) {
                Log.e("PdfPodcasterApp", "Failed to save crash log", e)
            }
            defaultHandler?.uncaughtException(thread, throwable)
        }
    }
}