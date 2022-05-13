package com.iadvize.conversation.sdk.demo.activities

import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import com.google.android.material.tabs.TabLayout
import com.iadvize.conversation.sdk.IAdvizeSDK
import com.iadvize.conversation.sdk.demo.R
import com.iadvize.conversation.sdk.demo.adapters.MainPagerAdapter
import com.iadvize.conversation.sdk.demo.databinding.MainActivityBinding
import com.iadvize.conversation.sdk.feature.authentication.AuthenticationOption
import com.iadvize.conversation.sdk.feature.chatbox.ChatboxConfiguration
import com.iadvize.conversation.sdk.feature.conversation.ConversationChannel
import com.iadvize.conversation.sdk.feature.conversation.ConversationListener
import com.iadvize.conversation.sdk.feature.conversation.IncomingMessageAvatar
import com.iadvize.conversation.sdk.feature.conversation.OngoingConversation
import com.iadvize.conversation.sdk.feature.defaultfloatingbutton.DefaultFloatingButtonConfiguration
import com.iadvize.conversation.sdk.feature.defaultfloatingbutton.DefaultFloatingButtonMargins
import com.iadvize.conversation.sdk.feature.defaultfloatingbutton.DefaultFloatingButtonOption
import com.iadvize.conversation.sdk.feature.gdpr.GDPREnabledOption
import com.iadvize.conversation.sdk.feature.gdpr.GDPRListener
import com.iadvize.conversation.sdk.feature.gdpr.GDPROption
import com.iadvize.conversation.sdk.feature.targeting.LanguageOption
import com.iadvize.conversation.sdk.feature.targeting.NavigationOption
import com.iadvize.conversation.sdk.feature.targeting.TargetingListener
import com.iadvize.conversation.sdk.feature.targeting.TargetingRule
import com.iadvize.conversation.sdk.type.Language
import java.net.URI
import java.util.*

/**
 * Created by Yann Coupé on 20/08/2018.
 * Copyright © 2018 iAdvize. All rights reserved.
 */
class MainActivity : AppCompatActivity(), GDPRListener, ConversationListener, TargetingListener {
    private lateinit var binding: MainActivityBinding

    /**
     * Your `projectId` and `targetingRuleId` are available on the iAdvize administration website.
     */
    private val projectId = 0
    private val targetingRule = TargetingRule(
        UUID.fromString("your-targeting-rule-id"),
        ConversationChannel.CHAT // or ConversationChannel.VIDEO
    )

    /**
     * This callback is related to the SDK Activation. Once the SDK is activated, you will be able
     * to activate your targeting rule.
     */
    private val sdkActivationCallback = object : IAdvizeSDK.Callback {
        override fun onFailure(t: Throwable) {
            Log.e("iAdvize SDK Demo", "The SDK activation failed with:", t)
        }

        override fun onSuccess() {
            Log.d("iAdvize SDK Demo", "The SDK has been activated.")
            IAdvizeSDK.targetingController.activateTargetingRule(targetingRule)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.main_activity)

        setSupportActionBar(binding.mainToolbar)

        binding.mainTablayout.addTab(binding.mainTablayout.newTab().setText("Catalog"))
        binding.mainTablayout.addTab(binding.mainTablayout.newTab().setText("Cart"))
        binding.mainTablayout.tabGravity = TabLayout.GRAVITY_FILL

        val adapter = MainPagerAdapter(supportFragmentManager, binding.mainTablayout.tabCount)
        binding.mainViewpager.adapter = adapter
        binding.mainViewpager.addOnPageChangeListener(
            TabLayout.TabLayoutOnPageChangeListener(binding.mainTablayout)
        )
        binding.mainTablayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabReselected(tab: TabLayout.Tab?) {}

            override fun onTabSelected(tab: TabLayout.Tab?) {
                tab?.let {
                    binding.mainViewpager.currentItem = it.position
                }
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {}
        })

        setupIAdvizeSDK()
    }

    /**
     * This method configures the iAdvize SDK by activating it, registering the targeting rule
     * events, registering the conversation events and configuring the chat button.
     */
    private fun setupIAdvizeSDK() {
        activateSDK()

        // Subscribe to targeting rule and conversation updates
        IAdvizeSDK.targetingController.listeners.add(this)
        IAdvizeSDK.conversationController.listeners.add(this)

        // By default, the iAdvize Conversation SDK take the device language
        IAdvizeSDK.targetingController.language = LanguageOption.Custom(Language.fr)

        // Update avatar for the incoming messages
        val avatar = ContextCompat.getDrawable(this, R.mipmap.ic_launcher)?.let {
            IncomingMessageAvatar.Image(it)
        }

        // Configure chatbox options
        IAdvizeSDK.chatboxController.setupChatbox(
            ChatboxConfiguration(
                mainColor = ContextCompat.getColor(this, R.color.colorPrimary),
                toolbarTitle = "Say Hello 👋",
                toolbarBackgroundColor = ContextCompat.getColor(this, R.color.colorPrimary),
                toolbarMainColor = Color.WHITE,
                fontPath = "fonts/ProximaNova-Regular.otf",
                automaticMessage = "Any question? Say Hello to Cooktoys and we will answer you as soon as possible! 😉",
                gdprMessage = "As part of the GDPR, we have to ask you to consent to our legal information.",
                incomingMessageAvatar = avatar
            )
        )

        // Configure default floating button
        IAdvizeSDK.defaultFloatingButtonController.setupDefaultFloatingButton(
            DefaultFloatingButtonOption.Enabled(
                DefaultFloatingButtonConfiguration(
                    anchor = Gravity.START or Gravity.BOTTOM,
                    margins = DefaultFloatingButtonMargins(),
                    backgroundTint = ContextCompat.getColor(this, R.color.colorPrimary),
                    iconTint = Color.WHITE,
                )
            )
        )
    }

    private fun activateSDK() {
        val shouldActivateGDPR = true // Switch to false to test without GDPR.

        if (shouldActivateGDPR) {
            iAdvizeActivateWithGDPRUrl() // or iAdvizeActivateWithGDPRListener()
        } else {
            iAdvizeActivate()
        }
    }

    /**
     * Replace "ConnectedUserIdentifier" by your user unique identifier (it should not be a personal
     * information of your user) so they can retrieve their conversation history across
     * installations and devices.
     */
    private fun iAdvizeActivate() {
        IAdvizeSDK.activate(
            projectId = projectId,
            authenticationOption = AuthenticationOption.Simple("ConnectedUserIdentifier"),
            gdprOption = GDPROption.Disabled,
            callback = sdkActivationCallback
        )
    }

    /**
     * To activate GDPR, you can provide a legal information URL. You can also activate the SDK with
     * an anonymous authentication.
     */
    private fun iAdvizeActivateWithGDPRUrl() {
        val legalInfoUri = URI.create("http://yourlegalinformationurl.com/legal")
        IAdvizeSDK.activate(
            projectId = projectId,
            authenticationOption = AuthenticationOption.Anonymous,
            gdprOption = GDPROption.Enabled(GDPREnabledOption.LegalUrl(legalInfoUri)),
            callback = sdkActivationCallback
        )
    }

    /**
     * To activate GDPR, you can use GDPR Listener
     */
    private fun iAdvizeActivateWithGDPRListener() {
        IAdvizeSDK.activate(
            projectId = projectId,
            authenticationOption = AuthenticationOption.Simple("ConnectedUserIdentifier"),
            gdprOption = GDPROption.Enabled(GDPREnabledOption.Listener(this)),
            callback = sdkActivationCallback
        )
    }

    override fun didTapMoreInformation() {
        Log.d("iAdvize SDK Demo", "User tap on More Information")
    }

    override fun handleClickedUrl(uri: Uri): Boolean {
        Log.d("iAdvize SDK Demo", "SDK received an url click event: $uri")
        return false
    }

    override fun onNewMessageReceived(content: String) {
        Log.d("iAdvize SDK Demo", "SDK received a new message: $content")
    }

    override fun onOngoingConversationUpdated(ongoingConversation: OngoingConversation?) {
        Log.d(
            "iAdvize SDK Demo",
            "SDK conversation update hasOngoingConversation: ${ongoingConversation != null}"
        )
    }

    override fun onActiveTargetingRuleAvailabilityUpdated(isActiveTargetingRuleAvailable: Boolean) {
        Log.d(
            "iAdvize SDK Demo",
            "iAdvize targeting rule availability updated: $isActiveTargetingRuleAvailable"
        )
    }
}