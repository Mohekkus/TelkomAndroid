package com.telkom.capex.ui.menu.dashboard.helper

import android.content.Context
import android.webkit.JavascriptInterface
import com.telkom.capex.R


class MyJavascriptInterface(private val context: Context) {

    @JavascriptInterface
    fun showDialog(value: String, percentage: String) {
        val splitter = value.split("\n")
        val title = splitter[0]
        val message = splitter[1]

        android.app.AlertDialog.Builder(context, R.style.AlertDialogTheme).apply {
            setTitle("$title ($percentage%)")
            setMessage(message)
        }
            .create()
            .also {
                it.apply {
                    window?.setBackgroundDrawableResource(R.drawable.drawable_rounded)
                }
            }
            .show()
    }
}