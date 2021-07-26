package com.example.staselovich_p3_l1.tools

import android.app.AlertDialog
import android.content.Context
import android.graphics.drawable.ColorDrawable
import com.example.staselovich_p3_l1.R

fun Context.showAlertDialog(): AlertDialog {
    val builder: AlertDialog.Builder = AlertDialog.Builder(this)
        .setView(R.layout.layout_progresbar)
    val dialog = builder.create()
    dialog.show()
    dialog.window?.setLayout(300,300)
    dialog.window?.setBackgroundDrawable(ColorDrawable(android.graphics.Color.TRANSPARENT))
    return dialog
}