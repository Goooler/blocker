package com.merxury.blocker.utils

import android.os.Environment
import android.util.Log
import com.merxury.blocker.core.root.RootCommand
import com.stericson.RootTools.RootTools
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream
import java.io.IOException


object FileUtils {
    const val TAG = "FileUtils"

    fun copy(source: String, dest: String): Boolean {
        Log.i(TAG, "Copy $source to $dest")
        try {
            FileInputStream(source).use({ input ->
                FileOutputStream(dest).use({ output ->
                    val buf = ByteArray(1024)
                    var length = input.read(buf)
                    while (length > 0) {
                        output.write(buf, 0, length)
                        length = input.read(buf)
                    }
                })
            })
        } catch (e: IOException) {
            e.printStackTrace()
            Log.e(TAG, e.message)
            return false
        }
        return true
    }

    fun cat(source: String, dest: String) {
        RootCommand.runBlockingCommand("cat $source > $dest")
    }

    fun test(path: String): Boolean {
        val output = RootCommand.runBlockingCommand("[ -f $path ] && echo \"yes\" || echo \"no\"")
        return when (output.trim()) {
            "yes" -> true
            else -> false
        }
    }

    fun listFiles(path: String): List<String> {
        val output = RootCommand.runBlockingCommand("find $path")
        if (output.contains("No such file or directory")) {
            return ArrayList()
        }
        val files = output.split("\n")
        return files.filterNot { it.isEmpty() || it == path }
    }

    fun copyWithRoot(source: String, dest: String): Boolean {
        Log.i(TAG, "Copy $source to $dest with root permission")
        return RootTools.copyFile(source, dest, false, true)
    }

    fun isExternalStorageWritable(): Boolean {
        return Environment.getExternalStorageState() == Environment.MEDIA_MOUNTED
    }

    fun isExternalStorageReadable(): Boolean {
        return Environment.getExternalStorageState() in
                setOf(Environment.MEDIA_MOUNTED, Environment.MEDIA_MOUNTED_READ_ONLY)
    }

    fun getExternalStoragePath(): String {
        return Environment.getExternalStorageDirectory().absolutePath + File.separator
    }
}