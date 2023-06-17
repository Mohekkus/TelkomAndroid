package com.telkom.capex.etc

import android.app.Activity
import android.content.Context
import android.os.IBinder
import android.view.View
import android.view.inputmethod.InputMethodManager
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.concurrent.Executors

object KeyboardUtils {
    private fun getInput(activity: Activity): InputMethodManager {
        return activity.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    }

    @OptIn(DelicateCoroutinesApi::class)
    fun show(activity: Activity, view: View){
        GlobalScope.launch {
            withContext(Dispatchers.IO){
                getInput(activity).showSoftInput(view, InputMethodManager.SHOW_IMPLICIT)
            }
        }
    }

    @OptIn(DelicateCoroutinesApi::class)
    fun hide(activity: Activity, binder: IBinder){
        GlobalScope.launch {
            withContext(Dispatchers.IO){
                getInput(activity).hideSoftInputFromWindow(binder, 0)
            }
        }
    }

    fun hide(activity: Activity, view: View){
        Executors.newSingleThreadExecutor().execute {
            getInput(activity).hideSoftInputFromWindow(view.windowToken, 0)
        }
    }
}