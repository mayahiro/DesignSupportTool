package org.mayahiro.designsupporttool.utils

import android.content.Context
import android.util.DisplayMetrics
import android.view.WindowManager

object DisplayMetricsUtils {

    fun displayMetrics(context: Context): DisplayMetrics {
        val displayMetrics = DisplayMetrics()

        val wm = context.getSystemService(Context.WINDOW_SERVICE) as WindowManager

        wm.defaultDisplay.getMetrics(displayMetrics)

        return displayMetrics
    }
}
