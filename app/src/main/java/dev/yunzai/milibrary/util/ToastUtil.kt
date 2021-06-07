package dev.yunzai.milibrary.util

import android.content.Context
import android.widget.Toast

fun Context.showToast(message: String, toastLength: Int = Toast.LENGTH_SHORT) {
    Toast.makeText(this, message, toastLength).show()
}
