package dev.yunzai.milibrary.base

import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import dev.yunzai.milibrary.R

fun Context.showProgressDialog(userDim: Boolean = true): AlertDialog {
    val builder: AlertDialog.Builder = AlertDialog.Builder(this)
    builder.setCancelable(false)
    builder.setView(R.layout.layout_loading_dialog)
    val dialog: AlertDialog = builder.create()
    dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
    if (!userDim)
        dialog.window?.setDimAmount(0F)
    dialog.show()
    return dialog
}

fun AlertDialog.hideProgressDialog() {
    this.dismiss()
}

fun Context.showAlertDialog(
    title: String,
    message: String,
    positiveButtonText: String,
    positiveCallback: (DialogInterface) -> Unit
) {
    val alertDialog = AlertDialog.Builder(this).apply {
        setTitle(title)
        setMessage(message)
        setCancelable(false)
        setPositiveButton(positiveButtonText) { dialog, _ ->
            positiveCallback.invoke(dialog)
        }
    }
    val dialog = alertDialog.create()
    dialog.setOnShowListener {
        dialog.window?.setBackgroundDrawableResource(
            R.drawable.bg_alert_dialog
        )
    }
    dialog.show()
}
