package com.iadvize.conversation.sdk.demo.activities

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.google.android.material.tabs.TabLayout
import com.iadvize.conversation.sdk.IAdvizeConversationManager
import com.iadvize.conversation.sdk.IAdvizeManager
import com.iadvize.conversation.sdk.demo.R
import com.iadvize.conversation.sdk.demo.adapters.MainPagerAdapter
import com.iadvize.conversation.sdk.enums.*
import com.iadvize.conversation.sdk.listener.ActivateListener
import com.iadvize.conversation.sdk.listener.GDPRListener
import com.iadvize.conversation.sdk.listener.IAdvizeConversationManagerListener
import com.iadvize.conversation.sdk.listener.SDKStatusListener
import com.iadvize.conversation.sdk.model.ConversationViewConfiguration
import com.iadvize.conversation.sdk.model.User
import com.iadvize.conversation.sdk.type.Language
import kotlinx.android.synthetic.main.activity_main.*
import java.net.URL
import java.util.* // ktlint-disable no-wildcard-imports

/**
 * Created by Yann Coupé on 20/08/2018.
 * Copyright © 2018 iAdvize. All rights reserved.
 */
class MainActivity : AppCompatActivity(), SDKStatusListener, IAdvizeConversationManagerListener, GDPRListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setSupportActionBar(main_toolbar)

        // Switch to false to test without GDPR.
        val shouldActivateGDPR = true

        if (shouldActivateGDPR) {
            iAdvizeActivateWithGDPRUrl() // iAdvizeActivateWithGDPRListener()
        } else {
            iAdvizeActivate()
        }

        // Management of the SDK status change
        IAdvizeManager.statusListener = this

        // Subscribe to new messages and unread messages counter events
        IAdvizeConversationManager.listener = this

        // By default, the iAdvize Conversation SDK take the device language
        IAdvizeManager.language = SDKLanguageOption.Custom(Language.FR)

        // Update avatar for the incoming messages
        val avatar = ContextCompat.getDrawable(this, R.mipmap.ic_launcher)?.let {
            IncomingMessageAvatar.Image(it)
        }

        // Configure SDK options for your integration
        val config = ConversationViewConfiguration(ContextCompat.getColor(this, R.color.colorPrimary),
                "Say Hello 👋",
                "Any question? Say Hello to Cooktoys and we will answer you as soon as possible! 😉",
                "As part of the GDPR, we have to ask you to consent to our legal information.",
                "fonts/ProximaNova-Regular.otf",
                ContextCompat.getColor(this, R.color.colorPrimary),
                Color.WHITE,
                avatar)

        // Apply this configuration
        IAdvizeConversationManager.setupConversationView(config)

        // Position of the chat button
        IAdvizeConversationManager.setChatButtonPosition(16, 16)

        main_tablayout.addTab(main_tablayout.newTab().setText("Catalog"))
        main_tablayout.addTab(main_tablayout.newTab().setText("Cart"))
        main_tablayout.tabGravity = TabLayout.GRAVITY_FILL

        val adapter = MainPagerAdapter(supportFragmentManager, main_tablayout.tabCount)
        main_viewpager.adapter = adapter
        main_viewpager.addOnPageChangeListener(TabLayout.TabLayoutOnPageChangeListener(main_tablayout))
        main_tablayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabReselected(tab: TabLayout.Tab?) {}

            override fun onTabSelected(tab: TabLayout.Tab?) {
                tab?.let {
                    main_viewpager.currentItem = it.position
                }
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {}
        })
    }

    private fun iAdvizeActivate() {
        // Replace "ConnectedUserIdentifier" by your user unique identifier (it should not be
        // a personal information of your user) so they can retrieve their conversation history
        // accross installations and devices.
        //
        // Your `iAdvizeSecret` is available on your app on the iAdvize Administration website.
        IAdvizeManager.activate(JWTOption.Secret("iAdvizeSecret"),
                "ConnectedUserIdentifier", GDPROption.Disabled(), UUID.fromString("RuleId"), object : ActivateListener {
            override fun onActivateFailure(t: Throwable) {
                // Activation fails. You need to retry later to be able to properly activate the iAdvize Conversation SDK.
            }
            override fun onActivateSuccess(isEnabled: Boolean) {
                // Activation succeeds. You are now able to provide a chat experience to your users now
                // or later by showing the chat button.
                if (isEnabled) {
                    IAdvizeConversationManager.showChatButton()
                    // Register user information which will be displayed to your operators or ibbü experts.
                    IAdvizeManager.registerUser(User("Antoine"))
                }
            }
        })
    }

    private fun iAdvizeActivateWithGDPRUrl() {
        // To activate GDPR, you have to provide a legal information URL.
        val legalInfoUrl = URL("http://yourlegalinformationurl.com/legal")
        IAdvizeManager.activate(JWTOption.Secret("iAdvizeSecret"),
                "ConnectedUserIdentifier", GDPROption.Enabled(GDPREnabledOption.LegalUrl(legalInfoUrl)), UUID.fromString("RuleId"), object : ActivateListener {
            override fun onActivateFailure(t: Throwable) {
                // Activation fails. You need to retry later to be able to properly activate the iAdvize Conversation SDK.
            }
            override fun onActivateSuccess(isEnabled: Boolean) {
                // Activation succeeds. You are now able to provide a chat experience to your users now
                // or later by showing the chat button.
                if (isEnabled) {
                    IAdvizeConversationManager.showChatButton()
                    // Register user information which will be displayed to your operators or ibbü experts.
                    IAdvizeManager.registerUser(User("Antoine"))
                }
            }
        })
    }

    private fun iAdvizeActivateWithGDPRListener() {
        // To activate GDPR, you have to provide a legal information URL.
        val legalInfoUrl = URL("http://yourlegalinformationurl.com/legal")
        IAdvizeManager.activate(JWTOption.Secret("iAdvizeSecret"),
                "ConnectedUserIdentifier", GDPROption.Enabled(GDPREnabledOption.Listener(this)), UUID.fromString("RuleId"), object : ActivateListener {
            override fun onActivateFailure(t: Throwable) {
                // Activation fails. You need to retry later to be able to properly activate the iAdvize Conversation SDK.
            }
            override fun onActivateSuccess(isEnabled: Boolean) {
                // Activation succeeds. You are now able to provide a chat experience to your users now
                // or later by showing the chat button.
                if (isEnabled) {
                    IAdvizeConversationManager.showChatButton()
                    // Register user information which will be displayed to your operators or ibbü experts.
                    IAdvizeManager.registerUser(User("Antoine"))
                }
            }
        })
    }

    override fun onSdkDisabled() {
        // By default, the SDK hide the chat button if it is visible
        Log.d("SDK Demo", "SDK has been disabled")
    }

    override fun onSdkEnabled() {
        Log.d("SDK Demo", "SDK has been enabled")
    }

    override fun didReceiveNewMessage(content: String) {
        Log.d("SDK Demo", "SDK receive a new message")
    }

    override fun didUpdateUnreadMessagesCount(unreadMessagesCount: Int) {
        Log.d("SDK Demo", "SDK update unread messages count")
    }

    override fun didOpenConversation() {
        Log.d("SDK Demo", "SDK conversation was opened")
    }

    override fun didTapMoreInformation() {
        Log.d("SDK Demo", "User tap on More Information")
    }
}