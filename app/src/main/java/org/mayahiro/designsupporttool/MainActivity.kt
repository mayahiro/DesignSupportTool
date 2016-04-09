package org.mayahiro.designsupporttool

import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.support.v7.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
import org.mayahiro.designsupporttool.services.OverlayShowService

class MainActivity : AppCompatActivity() {
    companion object {
        val REQUEST_OVERLAY_PERMISSION = 10000
    }

    private var service: Intent? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

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
