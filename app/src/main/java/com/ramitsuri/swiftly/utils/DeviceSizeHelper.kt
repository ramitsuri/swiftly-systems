package com.ramitsuri.swiftly.utils

import android.content.Context

object DeviceSizeHelper {
    fun getScreenWidth(context: Context): Int {
        return context.resources.displayMetrics.widthPixels
    }
}