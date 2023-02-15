package com.iadvize.conversation.sdk.demo.feature

import android.content.Context
import android.graphics.Color
import android.view.Gravity
import androidx.appcompat.content.res.AppCompatResources
import com.iadvize.conversation.sdk.demo.R
import com.iadvize.conversation.sdk.feature.authentication.AuthenticationOption
import com.iadvize.conversation.sdk.feature.chatbox.ChatboxConfiguration
import com.iadvize.conversation.sdk.feature.conversation.ConversationChannel
import com.iadvize.conversation.sdk.feature.conversation.IncomingMessageAvatar
import com.iadvize.conversation.sdk.feature.defaultfloatingbutton.DefaultFloatingButtonConfiguration
import com.iadvize.conversation.sdk.feature.defaultfloatingbutton.DefaultFloatingButtonOption
import com.iadvize.conversation.sdk.feature.gdpr.GDPREnabledOption
import com.iadvize.conversation.sdk.feature.gdpr.GDPROption
import com.iadvize.conversation.sdk.feature.logger.Logger
import com.iadvize.conversation.sdk.feature.targeting.LanguageOption
import com.iadvize.conversation.sdk.feature.targeting.TargetingRule
import com.iadvize.conversation.sdk.type.Language
import java.net.URI
import java.util.UUID

object IAdvizeSDKConfig {
    // Log
    val logLevel = Logger.Level.VERBOSE

    // Authentication
    const val projectId = -1 // TODO Replace with your project id

    // TODO Choose the correct auth option for your use case
    // val authOption = AuthenticationOption.Anonymous
    val authOption = AuthenticationOption.Simple("your-user-unique-simple-identifier")
//    val authOption = AuthenticationOption.Secured(object : AuthenticationOption.JWEProvider {
//        override fun onJWERequested(callback: AuthenticationOption.JWECallback) {
//            // Fetch JWE from your own secure auth process
//            callback.onJWERetrieved(jwe)
//            // or callback.onJWEFailure(exception)
//        }
//    })

    // Targeting
    private const val targetingRuleId = "your-targeting-rule" // TODO Replace with your rule id
    private val targetingRuleChannel = ConversationChannel.CHAT // or ConversationChannel.VIDEO
    val targetingLanguage = LanguageOption.Custom(Language.en)
    val targetingRule = TargetingRule(UUID.fromString(targetingRuleId), targetingRuleChannel)

    // Conversation
    val gdprOption =
        GDPROption.Enabled(GDPREnabledOption.LegalUrl(URI("https://my.legal.gdpr.uri")))
    // val gdprOption = GDPROption.Disabled

    // Default Floating Button
    fun defaultFloatingButtonOption(context: Context) = DefaultFloatingButtonOption.Enabled(
        DefaultFloatingButtonConfiguration(
            anchor = Gravity.BOTTOM or Gravity.END,
            backgroundTint = context.resources.getColor(R.color.outer_space),
            iconResIds = mapOf(
                ConversationChannel.CHAT to R.drawable.ic_logo_small,
                ConversationChannel.VIDEO to R.drawable.ic_logo_small
            ),
            iconTint = Color.TRANSPARENT
        )
    )

    // Chatbox
    fun chatboxConfiguration(context: Context) = ChatboxConfiguration(
        automaticMessage = "Welcome to Smart Livechat! What can we do for you?",
        fontPath = "fonts/montserrat.ttf",
        gdprMessage = "For a better support, we need to save the history of the exchanges and view your activity on the mobile app during the conversation.",
        incomingMessageAvatar = IncomingMessageAvatar.Image(
            AppCompatResources.getDrawable(
                context,
                R.mipmap.ic_launcher
            )!!
        ),
        mainColor = context.resources.getColor(R.color.outer_space),
        toolbarBackgroundColor = context.resources.getColor(R.color.outer_space),
        toolbarMainColor = context.resources.getColor(R.color.whisper),
        toolbarTitle = "Smart Livechat"
    )
}
