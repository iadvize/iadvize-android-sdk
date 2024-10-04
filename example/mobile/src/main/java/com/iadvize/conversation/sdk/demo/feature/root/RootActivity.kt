package com.iadvize.conversation.sdk.demo.feature.root

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.iadvize.conversation.sdk.demo.databinding.RootActivityBinding
import dev.chrisbanes.insetter.applyInsetter

class RootActivity : AppCompatActivity() {
    private lateinit var binding: RootActivityBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = RootActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)

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
