package org.mayahiro.designsupporttool

import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.support.v7.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.toolbar.*
import org.mayahiro.designsupporttool.services.OverlayShowService
import org.mayahiro.designsupporttool.utils.DisplayMetricsUtils

class MainActivity : AppCompatActivity() {
    companion object {
        val REQUEST_OVERLAY_PERMISSION = 10000
    }

    private var service: Intent? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setSupportActionBar(toolbar)
        val actionBar = supportActionBar
        actionBar?.setTitle(R.string.app_name)

        service = Intent(this, OverlayShowService::class.java)

        start_button.setOnClickListener {
            if (checkOverlayPermission()) {
                startService(service)
            } else {
                val intent = Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION, Uri.parse("package:$packageName"))
                startActivityForResult(intent, REQUEST_OVERLAY_PERMISSION)
            }
        }

        stop_button.setOnClickListener {
            stopService(service)
        }

        // build information
        os_version_text_view.text = Build.VERSION.RELEASE
        api_version_text_view.text = Build.VERSION.SDK_INT.toString()
        brand_text_view.text = Build.BRAND
        manufacturer_text_view.text = Build.MANUFACTURER
        model_text_view.text = Build.MODEL

        // display metrics
        val displayMetrics = DisplayMetricsUtils.displayMetrics(this)

        density_text_view.text = displayMetrics.density.toString()
        height_pixels_text_view.text = "%dpx(%.02fdp)".format(displayMetrics.heightPixels, displayMetrics.heightPixels / displayMetrics.density)
        width_pixels_text_view.text = "%dpx(%.02fdp)".format(displayMetrics.widthPixels, displayMetrics.widthPixels / displayMetrics.density)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        when (requestCode) {
            REQUEST_OVERLAY_PERMISSION -> {
                if (checkOverlayPermission()) {
                    startService(service)
                }
            }
        }
        super.onActivityResult(requestCode, resultCode, data)
    }

    private fun checkOverlayPermission(): Boolean {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            return true
        }

        return Settings.canDrawOverlays(this)
    }
}
