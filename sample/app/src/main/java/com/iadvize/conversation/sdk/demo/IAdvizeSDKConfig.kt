package com.iadvize.conversation.sdk.demo

import android.graphics.Color
import android.view.Gravity
import com.iadvize.conversation.sdk.feature.authentication.AuthenticationOption
import com.iadvize.conversation.sdk.feature.defaultfloatingbutton.DefaultFloatingButtonConfiguration
import com.iadvize.conversation.sdk.feature.defaultfloatingbutton.DefaultFloatingButtonOption
import com.iadvize.conversation.sdk.feature.gdpr.GDPREnabledOption
import com.iadvize.conversation.sdk.feature.gdpr.GDPROption
import com.iadvize.conversation.sdk.type.Language
import java.net.URI

object IAdvizeSDKConfig {
    // Authentication
    val projectId = 3585
    val authOption = AuthenticationOption.Simple("B956F914-F893-4FE9-9A50-CDC836CC7BD7")
    // val authOption = AuthenticationOption.Anonymous

    // Targeting
    val targetingRuleId = "a41611fe-c453-4df5-b6ef-3438527933b4"
    val targetingLanguage = Language.fr

    // Default Floating Button
    val defaultFloatingButtonOption = DefaultFloatingButtonOption.Enabled(
        DefaultFloatingButtonConfiguration(
            anchor = Gravity.BOTTOM or Gravity.END,
            backgroundTint = Color.BLACK
        )
    )

    // Conversation
    val gdprOption =
        GDPROption.Enabled(GDPREnabledOption.LegalUrl(URI("https://my.legal.gdpr.uri")))
    // val gdprOption = GDPROption.Disabled


    // project id, targeting rule, user ID, GDPR, couleurs, type de bouton etc...
}
