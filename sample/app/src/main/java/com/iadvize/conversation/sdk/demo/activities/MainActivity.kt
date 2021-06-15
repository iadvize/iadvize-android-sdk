package com.iadvize.conversation.sdk.demo.activities

import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.google.android.material.tabs.TabLayout
import com.iadvize.conversation.sdk.IAdvizeSDK
import com.iadvize.conversation.sdk.controller.conversation.ConversationListener
import com.iadvize.conversation.sdk.controller.targeting.TargetingListener
import com.iadvize.conversation.sdk.demo.R
import com.iadvize.conversation.sdk.demo.adapters.MainPagerAdapter
import com.iadvize.conversation.sdk.model.IAdvizeSDKCallback
import com.iadvize.conversation.sdk.model.auth.AuthenticationOption
import com.iadvize.conversation.sdk.model.configuration.ChatboxConfiguration
import com.iadvize.conversation.sdk.model.conversation.IncomingMessageAvatar
import com.iadvize.conversation.sdk.model.gdpr.GDPREnabledOption
import com.iadvize.conversation.sdk.model.gdpr.GDPRListener
import com.iadvize.conversation.sdk.model.gdpr.GDPROption
import com.iadvize.conversation.sdk.model.language.SDKLanguageOption
import com.iadvize.conversation.sdk.type.Language
import kotlinx.android.synthetic.main.activity_main.*
import java.net.URI
import java.util.*

/**
 * Created by Yann Coupé on 20/08/2018.
 * Copyright © 2018 iAdvize. All rights reserved.
 */
class MainActivity : AppCompatActivity(), GDPRListener, ConversationListener, TargetingListener {

    /**
     * Your `projectId` and `targetingRuleId` are available on your app on the iAdvize
     * administration website.
     */
    private val projectId = 0
    private val targetingRuleId = "your-targeting-rule-id"

    /**
     * This callback is related to the SDK Activation. Once the SDK is activated, you will be able
     * to activate your targeting rule.
     */
    private val sdkActivationCallback = object : IAdvizeSDKCallback {
        override fun onFailure(t: Throwable) {
            Log.e("iAdvize SDK Demo", "The SDK activation failed with:", t)
        }

        override fun onSuccess() {
            Log.d("iAdvize SDK Demo", "The SDK has been activated.")
            IAdvizeSDK.targetingController.activateTargetingRule(UUID.fromString(targetingRuleId))
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setSupportActionBar(main_toolbar)

        main_tablayout.addTab(main_tablayout.newTab().setText("Catalog"))
        main_tablayout.addTab(main_tablayout.newTab().setText("Cart"))
        main_tablayout.tabGravity = TabLayout.GRAVITY_FILL

        val adapter = MainPagerAdapter(supportFragmentManager, main_tablayout.tabCount)
        main_viewpager.adapter = adapter
        main_viewpager.addOnPageChangeListener(
            TabLayout.TabLayoutOnPageChangeListener(
                main_tablayout
            )
        )
        main_tablayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabReselected(tab: TabLayout.Tab?) {}

            override fun onTabSelected(tab: TabLayout.Tab?) {
                tab?.let {
                    main_viewpager.currentItem = it.position
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
        IAdvizeSDK.targetingController.language = SDKLanguageOption.Custom(Language.FR)

        // Update avatar for the incoming messages
        val avatar = ContextCompat.getDrawable(this, R.mipmap.ic_launcher)?.let {
            IncomingMessageAvatar.Image(it)
        }

        // Configure SDK options for your integration
        val config = ChatboxConfiguration(
            mainColor = ContextCompat.getColor(this, R.color.colorPrimary),
            toolbarTitle = "Say Hello 👋",
            automaticMessage = "Any question? Say Hello to Cooktoys and we will answer you as soon as possible! 😉",
            gdprMessage = "As part of the GDPR, we have to ask you to consent to our legal information.",
            fontPath = "fonts/ProximaNova-Regular.otf",
            ContextCompat.getColor(this, R.color.colorPrimary),
            Color.WHITE,
            avatar
        )

        // Apply this configuration
        IAdvizeSDK.chatboxController.setupChatbox(config)

        // Position of the chat button
        IAdvizeSDK.chatboxController.setChatButtonPosition(16, 16)
        IAdvizeSDK.chatboxController.useDefaultChatButton = true
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
            gdprOption = GDPROption.Disabled(),
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
            authenticationOption = AuthenticationOption.Anonymous(),
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

    override fun onOngoingConversationStatusChanged(hasOngoingConversation: Boolean) {
        Log.d("iAdvize SDK Demo", "SDK update hasOngoingConversation: $hasOngoingConversation")
    }

    override fun onActiveTargetingRuleAvailabilityUpdated(isActiveTargetingRuleAvailable: Boolean) {
        Log.d(
            "iAdvize SDK Demo",
            "iAdvize targeting rule availability updated: $isActiveTargetingRuleAvailable"
        )
    }
}