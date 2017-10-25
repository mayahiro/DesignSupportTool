package org.mayahiro.designsupporttool.ext

import android.content.Context

fun Context.displayWidth(): Int = this.resources.displayMetrics.widthPixels

fun Context.displayHeight(): Int = this.resources.displayMetrics.heightPixels

fun Context.density(): Float = this.resources.displayMetrics.density

