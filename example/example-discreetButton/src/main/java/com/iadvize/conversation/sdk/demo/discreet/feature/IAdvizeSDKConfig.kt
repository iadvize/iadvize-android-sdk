package com.iadvize.conversation.sdk.demo.discreet.feature

import android.widget.Toast
import androidx.appcompat.content.res.AppCompatResources
import com.iadvize.conversation.sdk.IAdvizeSDK
import com.iadvize.conversation.sdk.demo.discreet.R
import com.iadvize.conversation.sdk.demo.feature.ConversationFlowManager
import com.iadvize.conversation.sdk.feature.authentication.AuthenticationOption
import com.iadvize.conversation.sdk.feature.chatbox.ChatboxConfiguration
import com.iadvize.conversation.sdk.feature.conversation.ConversationChannel
import com.iadvize.conversation.sdk.feature.conversation.IncomingMessageAvatar
import com.iadvize.conversation.sdk.feature.defaultfloatingbutton.DefaultFloatingButtonOption
import com.iadvize.conversation.sdk.feature.gdpr.GDPROption
import com.iadvize.conversation.sdk.feature.logger.Logger
import com.iadvize.conversation.sdk.feature.targeting.LanguageOption
import com.iadvize.conversation.sdk.feature.targeting.TargetingRule
import com.iadvize.conversation.sdk.type.Language
import java.util.UUID

object IAdvizeSDKConfig {
    private const val targetingRuleId = "your-targeting-rule" // TODO Replace with your rule id
    val targetingRule = TargetingRule(UUID.fromString(targetingRuleId), ConversationChannel.CHAT)

    fun setup(app: App) {
        val res = app.resources

        // Initiate the iAdvize SDK.
        IAdvizeSDK.logLevel = Logger.Level.VERBOSE
        IAdvizeSDK.initiate(app)

        // Configure the targeting language (should match the user language in a real use case)
        IAdvizeSDK.targetingController.language = LanguageOption.Custom(Language.en)

        // Disable iAdvize Default Floating Button
        IAdvizeSDK.defaultFloatingButtonController.setupDefaultFloatingButton(
            DefaultFloatingButtonOption.Disabled
        )

        // Hook to iAdvize conversation flow
        val flowManager = ConversationFlowManager()
        IAdvizeSDK.targetingController.listeners.add(flowManager)
        IAdvizeSDK.conversationController.listeners.add(flowManager)

        // Configure iAdvize Chatbox look & feel
        IAdvizeSDK.chatboxController.setupChatbox(
            ChatboxConfiguration(
                fontPath = "fonts/montserrat.ttf",
                accentColor = app.resources.getColor(R.color.malachite),
                incomingMessageBackgroundColor = res.getColor(R.color.whisper),
                incomingMessageTextColor = res.getColor(R.color.outer_space),
                incomingMessageStrokeColor = null,
                outgoingMessageBackgroundColor = res.getColor(R.color.outer_space),
                outgoingMessageTextColor = res.getColor(R.color.whisper),
                outgoingMessageStrokeColor = null,
                toolbarTitle = "Smart Livechat",
                toolbarBackgroundColor = res.getColor(R.color.outer_space),
                toolbarMainColor = res.getColor(R.color.whisper),
                automaticMessage = "Welcome to Smart Livechat! What can we do for you?",
                gdprMessage = "For a better support, we need to save the history of the exchanges and view your activity on the mobile app during the conversation.",
                incomingMessageAvatar = IncomingMessageAvatar.Image(
                    AppCompatResources.getDrawable(app, R.mipmap.ic_launcher)!!
                )
            )
        )

        // Activate the iAdvize SDK (start a user session)
        val projectId = -1 // TODO Replace with your project id
        val authOption = AuthenticationOption.Anonymous
        val gdprOption = GDPROption.Disabled
        IAdvizeSDK.activate(
            projectId,
            authOption,
            gdprOption,
            object : IAdvizeSDK.Callback {
                override fun onSuccess() {
                    Toast
                        .makeText(app, "🎉 iAdvize SDK Activation success", Toast.LENGTH_SHORT)
                        .show()
                }

                override fun onFailure(t: Throwable) {
                    Toast
                        .makeText(app, "❌ iAdvize SDK Activation failure", Toast.LENGTH_SHORT)
                        .show()
                }
            }
        )
    }
}
