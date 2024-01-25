package com.iadvize.conversation.sdk.demo.integratedbanner.feature.root

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.iadvize.conversation.sdk.demo.integratedbanner.databinding.RootActivityBinding

class RootActivity : AppCompatActivity() {
    private lateinit var binding: RootActivityBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = RootActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}
