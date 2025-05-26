package com.iadvize.conversation.sdk.demo.feature.root

import android.Manifest.permission.POST_NOTIFICATIONS
import android.os.Build.VERSION.SDK_INT
import android.os.Build.VERSION_CODES.TIRAMISU
import android.os.Bundle
import android.util.Log
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.iadvize.conversation.sdk.demo.databinding.RootActivityBinding
import dev.chrisbanes.insetter.applyInsetter

class RootActivity : AppCompatActivity() {
    private lateinit var binding: RootActivityBinding

    private val notificationPermissionLauncher =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { granted ->
            Log.v("RootActivity", "Notification Permission ${if (granted) "granted" else "denied"}")
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = RootActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Request notif permission for Android 13
        if (SDK_INT >= TIRAMISU) notificationPermissionLauncher.launch(POST_NOTIFICATIONS)

        applyInsets()
    }

    private fun applyInsets() {
        binding.toolbar.root.applyInsetter {
            type(statusBars = true) { margin(top = true) }
        }
        binding.navHostFragment.applyInsetter {
            type(navigationBars = true, ime = true) { padding(bottom = true) }
        }
    }
}
