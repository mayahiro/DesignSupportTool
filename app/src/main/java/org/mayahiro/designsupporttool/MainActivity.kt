package org.mayahiro.designsupporttool

import android.content.Intent
import android.databinding.DataBindingUtil
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.support.v7.app.AppCompatActivity
import org.mayahiro.designsupporttool.databinding.ActivityMainBinding
import org.mayahiro.designsupporttool.ext.density
import org.mayahiro.designsupporttool.ext.displayHeight
import org.mayahiro.designsupporttool.ext.displayWidth
import org.mayahiro.designsupporttool.services.OverlayShowService

class MainActivity : AppCompatActivity() {
    companion object {
        val REQUEST_OVERLAY_PERMISSION = 10000
    }

    private lateinit var binding: ActivityMainBinding

    private var service: Intent? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        setSupportActionBar(binding.includedToolbar.toolbar)
        val actionBar = supportActionBar
        actionBar?.setTitle(R.string.app_name)

        service = Intent(this, OverlayShowService::class.java)

        binding.startButton.setOnClickListener {
            if (checkOverlayPermission()) {
                startService(service)
            } else {
                val intent = Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION, Uri.parse("package:$packageName"))
                startActivityForResult(intent, REQUEST_OVERLAY_PERMISSION)
            }
        }

        binding.stopButton.setOnClickListener {
            stopService(service)
        }

        // build information
        binding.osVersionTextView.text = Build.VERSION.RELEASE
        binding.apiVersionTextView.text = Build.VERSION.SDK_INT.toString()
        binding.brandTextView.text = Build.BRAND
        binding.manufacturerTextView.text = Build.MANUFACTURER
        binding.modelTextView.text = Build.MODEL

        binding.densityTextView.text = density().toString()
        binding.heightPixelsTextView.text = "${displayHeight()}px(${displayHeight() / density()}dp)"
        binding.widthPixelsTextView.text = "${displayWidth()}px(${displayWidth() / density()}dp)"
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
