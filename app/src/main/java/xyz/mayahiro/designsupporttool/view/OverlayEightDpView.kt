package xyz.mayahiro.designsupporttool.view

import android.content.Context
import android.content.res.Configuration
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.util.DisplayMetrics
import android.view.View
import android.view.WindowManager

class OverlayEightDpView @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0) : View(context, attrs, defStyleAttr) {
    private val metrics: DisplayMetrics
    private val paint: Paint
    private val redPaint: Paint
    private val base: Float

    init {
        val wm = context.getSystemService(Context.WINDOW_SERVICE) as WindowManager
        metrics = DisplayMetrics()
        wm.defaultDisplay.getMetrics(metrics)

        base = 8 * metrics.density

        paint = Paint()
        paint.strokeWidth = 1f
        paint.color = Color.argb(100, 0, 0, 0)

        redPaint = Paint()
        redPaint.strokeWidth = 1f
        redPaint.color = Color.argb(255, 255, 0, 0)
    }

    override fun onConfigurationChanged(newConfig: Configuration?) {
        super.onConfigurationChanged(newConfig)

        val wm = context.getSystemService(Context.WINDOW_SERVICE) as WindowManager
        wm.defaultDisplay.getMetrics(metrics)
    }

    override fun onDraw(canvas: Canvas) {
        var i = base
        while (i < metrics.widthPixels) {
            if (i % (base * 5) == 0f) {
                canvas.drawLine(i - 0.5f, 0f, i - 0.5f, metrics.heightPixels.toFloat(), redPaint)
            } else {
                canvas.drawLine(i - 0.5f, 0f, i - 0.5f, metrics.heightPixels.toFloat(), paint)
            }

            i += base
        }

        var j = base
        while (j < metrics.heightPixels) {
            canvas.drawLine(0f, j - 0.5f, metrics.widthPixels.toFloat(), j - 0.5f, paint)

            j += base
        }

        super.onDraw(canvas)
    }
}
