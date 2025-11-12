package ru.hopenz.pratcticandroid.gp.utils

import android.content.Context
import androidx.core.content.FileProvider
import android.net.Uri
import java.io.File
import java.text.SimpleDateFormat
import java.util.*

object FileUtils {
    fun createTempImageUri(context: Context): Uri {
        val time = SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(Date())
        val file = File(context.cacheDir, "camera/$time.jpg")
        file.parentFile?.mkdirs()
        return FileProvider.getUriForFile(context, "${context.packageName}.fileprovider", file)
    }
}