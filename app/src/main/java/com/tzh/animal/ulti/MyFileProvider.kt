package com.tzh.animal.ulti

import android.content.Context
import android.os.Environment
import androidx.core.content.FileProvider
import com.tzh.animal.R
import java.io.File
import java.io.IOException


class MyFileProvider : FileProvider(R.xml.file_provider_paths) {
    companion object {
        const val PREF_NAME = "image_counter"
        const val KEY_COUNTER = "image_counter_key"

        private fun generateImageName(context: Context): String {
            val sharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
            val currentCounter = sharedPreferences.getInt(KEY_COUNTER, 1)
            return "animal_$currentCounter"
        }

        fun getDownloadFilePath(context: Context): File {
            val imageName = generateImageName(context)
            val storageDir =
                Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES)
            try {
                val file = File(storageDir, "$imageName.jpg")
                return file.apply {
                    createNewFile()
                }
            } catch (e: IOException) {
                e.printStackTrace()
            }
            return File.createTempFile(generateImageName(context), ".jpg", storageDir)
        }
    }

}
