package com.iadvize.conversation.sdk.demo.navigationitem.feature.root

import android.animation.LayoutTransition
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.iadvize.conversation.sdk.IAdvizeSDK
import com.iadvize.conversation.sdk.demo.feature.HideButton
import com.iadvize.conversation.sdk.demo.feature.ShowButton
import com.iadvize.conversation.sdk.demo.navigationitem.R
import com.iadvize.conversation.sdk.demo.navigationitem.databinding.RootActivityBinding
import com.iadvize.conversation.sdk.demo.navigationitem.feature.uiScope
import com.iadvize.conversation.sdk.demo.utility.on

class RootActivity : AppCompatActivity() {
    private lateinit var binding: RootActivityBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = RootActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)

        refreshNavController()

        // Activate layout update on change
        binding.bottomNavigation.layoutTransition.enableTransitionType(LayoutTransition.CHANGING)

        // Open chatbox on nav item click
        binding.bottomNavigation.menu.findItem(R.id.menuChat).setOnMenuItemClickListener {
            IAdvizeSDK.chatboxController.presentChatbox(this)
            true
        }

        // Hook to button update events
        on<ShowButton> { enableChat() }
        on<HideButton> { disableChat() }
    }

    private fun refreshNavController() {
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        binding.bottomNavigation.setupWithNavController(navHostFragment.navController)
    }

    private fun enableChat() = uiScope {
        binding.bottomNavigation.menu.findItem(R.id.menuChat).isVisible = true
    }

    private fun disableChat() = uiScope {
        binding.bottomNavigation.menu.findItem(R.id.menuChat).isVisible = false
    }
}
