package com.iadvize.conversation.sdk.demo.feature

import android.content.Context
import android.graphics.Color
import android.view.Gravity
import androidx.core.content.res.ResourcesCompat
import androidx.core.net.toUri
import com.iadvize.conversation.sdk.demo.R
import com.iadvize.conversation.sdk.feature.authentication.AuthenticationOption
import com.iadvize.conversation.sdk.feature.chatbox.ChatboxConfiguration
import com.iadvize.conversation.sdk.feature.defaultfloatingbutton.DefaultFloatingButtonConfiguration
import com.iadvize.conversation.sdk.feature.defaultfloatingbutton.DefaultFloatingButtonOption
import com.iadvize.conversation.sdk.feature.gdpr.GDPREnabledOption
import com.iadvize.conversation.sdk.feature.gdpr.GDPROption
import com.iadvize.conversation.sdk.feature.logger.Logger
import com.iadvize.conversation.sdk.feature.targeting.LanguageOption
import com.iadvize.conversation.sdk.feature.targeting.TargetingRule
import com.iadvize.conversation.sdk.type.Language
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
    val targetingLanguage = LanguageOption.Custom(Language.en)
    val targetingRule = TargetingRule(UUID.fromString(targetingRuleId))

    // Conversation
    val gdprOption =
        GDPROption.Enabled(GDPREnabledOption.LegalUrl("https://my.legal.gdpr.uri".toUri()))
    // val gdprOption = GDPROption.Disabled

    // Default Floating Button
    fun defaultFloatingButtonOption(context: Context) = DefaultFloatingButtonOption.Enabled(
        DefaultFloatingButtonConfiguration(
            anchor = Gravity.BOTTOM or Gravity.END,
            backgroundTint = context.resources.getColor(R.color.outer_space),
            iconResId = R.drawable.ic_logo_small,
            iconTint = Color.TRANSPARENT
        )
    )

    // Chatbox
    fun chatboxConfiguration(context: Context) = ChatboxConfiguration(
        primaryColor = context.resources.getColor(R.color.outer_space),
        primaryTextColor = context.resources.getColor(R.color.whisper),
        secondaryColor = context.resources.getColor(R.color.malachite),
        secondaryTextColor = context.resources.getColor(R.color.whisper),
        font = ResourcesCompat.getFont(context, R.font.opensans),
        title = "Smart Livechat",
        avatar = ResourcesCompat.getDrawable(context.resources, R.mipmap.ic_launcher, null),
        automaticMessage = "Welcome to Smart Livechat! What can we do for you?",
        gdprMessage = "For a better support, we need to save the history of the exchanges and view your activity on the mobile app during the conversation."
    )
}
