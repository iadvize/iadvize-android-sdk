package com.iadvize.conversation.sdk.demo.feature

import android.content.Context
import androidx.core.content.res.ResourcesCompat
import androidx.core.net.toUri
import com.iadvize.conversation.sdk.demo.R
import com.iadvize.conversation.sdk.feature.authentication.AuthenticationOption
import com.iadvize.conversation.sdk.feature.chatbox.ChatboxConfiguration
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

    // val authOption = AuthenticationOption.Anonymous
    val authOption = AuthenticationOption.Simple("your-user-unique-simple-identifier")
//    val authOption = AuthenticationOption.Secured(object : AuthenticationOption.JWEProvider {
//        // Coroutine-based: fetch the JWE from your own secure auth process and return it,
//        // or throw to signal a failure.
//        override suspend fun onJWERequested(): String = fetchJwe()
//
//        // Or, if you prefer callbacks, implement this variant instead:
//        // override fun onJWERequested(callback: AuthenticationOption.JWECallback) {
//        //     callback.onJWERetrieved(jwe) // or callback.onJWEFailure(exception)
//        // }
//    })

    // Targeting
    private const val targetingRuleId = "your-targeting-rule" // TODO Replace with your rule id
    val targetingLanguage = LanguageOption.Custom(Language.en)
    val targetingRule = TargetingRule(UUID.fromString(targetingRuleId))

    // Conversation
    val gdprOption =
        GDPROption.Enabled(GDPREnabledOption.LegalUrl("https://my.legal.gdpr.uri".toUri()))
    // val gdprOption = GDPROption.Disabled

    // Chatbox
    fun chatboxConfiguration(context: Context) = ChatboxConfiguration(
        primaryColor = context.resources.getColor(R.color.outer_space, null),
        primaryTextColor = context.resources.getColor(R.color.whisper, null),
        secondaryColor = context.resources.getColor(R.color.turquoise, null),
        secondaryTextColor = context.resources.getColor(R.color.whisper, null),
        font = ResourcesCompat.getFont(context, R.font.opensans),
        title = "AI Copilot",
        avatar = ResourcesCompat.getDrawable(context.resources, R.drawable.ic_logo, null),
        automaticMessage = "Welcome to Smart Livechat! What can we do for you?",
        gdprMessage = "For a better support, we need to save the history of the exchanges and view your activity on the mobile app during the conversation."
    )
}
