package com.winas_lesson.android.day4.day4homework.util

import android.app.Activity
import android.widget.Toast
import java.io.BufferedReader
import java.io.InputStreamReader

fun Activity.showToast(text: String) {
    val toast = Toast.makeText(this, text, Toast.LENGTH_SHORT)
    toast.show()
}

fun Activity.readStringFromAssetFile(filename: String): String {
    val stream = assets.open(filename)
    val bufferedReader = BufferedReader(InputStreamReader(stream))
    var data = ""
    var str = bufferedReader.readLine()
    while (str != null) {
        data += str
        str = bufferedReader.readLine()
    }
    return data
}
