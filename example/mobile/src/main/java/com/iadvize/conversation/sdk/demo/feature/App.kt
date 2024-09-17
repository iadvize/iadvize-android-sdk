package com.iadvize.conversation.sdk.demo.feature

import android.widget.Toast
import androidx.multidex.MultiDexApplication
import com.iadvize.conversation.sdk.IAdvizeSDK
import com.iadvize.conversation.sdk.demo.feature.notifications.NotificationService

class App : MultiDexApplication() {
    override fun onCreate() {
        super.onCreate()

        // Initiate the notification service
        NotificationService.init(this)

        // Initiate the iAdvize SDK.
        IAdvizeSDK.logLevel = IAdvizeSDKConfig.logLevel
        IAdvizeSDK.initiate(this)

        // Configure the targeting language (should match the user language in a real use case)
        IAdvizeSDK.targetingController.language = IAdvizeSDKConfig.targetingLanguage

        // Configure iAdvize Default Floating Button
        IAdvizeSDK.defaultFloatingButtonController.setupDefaultFloatingButton(
            IAdvizeSDKConfig.defaultFloatingButtonOption(this)
        )

        // Configure iAdvize Chatbox
        IAdvizeSDK.chatboxController.setupChatbox(
            IAdvizeSDKConfig.chatboxConfiguration(this)
        )

        // Activate the iAdvize SDK (start a user session)
        IAdvizeSDK.activate(
            IAdvizeSDKConfig.projectId,
            IAdvizeSDKConfig.authOption,
            IAdvizeSDKConfig.gdprOption,
            object : IAdvizeSDK.Callback {
                override fun onSuccess() {
                    Toast.makeText(
                        baseContext,
                        "🎉 iAdvize SDK Activation success",
                        Toast.LENGTH_SHORT
                    ).show()
                }

                override fun onFailure(error: IAdvizeSDK.Error) {
                    Toast.makeText(
                        baseContext,
                        "❌ iAdvize SDK Activation failure",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        )
    }
}
