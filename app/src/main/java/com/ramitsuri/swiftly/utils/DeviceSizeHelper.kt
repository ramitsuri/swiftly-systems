package com.ramitsuri.swiftly.utils

import android.content.Context
import android.util.TypedValue

object DeviceSizeHelper {
    fun getScreenWidth(context: Context): Int {
        return context.resources.displayMetrics.widthPixels
    }

    fun dpToPx(dp: Float, context: Context): Int {
        return TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_DIP,
            dp,
            context.resources.displayMetrics
        ).toInt()
    }

    fun pxToDp(px: Float, context: Context): Int {
        val density = context.resources.displayMetrics.density
        return (px / density).toInt()
    }

    /**
     * The maxSizeInParts determines how many divisible units fit into the full width of the phone.
     *
     * For example: if maxSizeInParts is 8 and the total screen width is 360px then each part is
     * 360px/8 = 45px.
     * Which means width of 6 parts translates to 270px.
     */
    fun getRequestedSizeInPx(
        requestedSizeInParts: Int,
        maxSizeInParts: Int,
        screenWidthInPx: Int
    ): Int {
        return screenWidthInPx * (requestedSizeInParts / maxSizeInParts)
    }
}