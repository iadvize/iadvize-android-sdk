package com.iadvize.conversation.sdk.demo.feature

import android.app.Application
import android.widget.Toast
import com.iadvize.conversation.sdk.IAdvizeSDK
import com.iadvize.conversation.sdk.demo.feature.notifications.NotificationService
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch

class App : Application() {
    private val scope = CoroutineScope(SupervisorJob() + Dispatchers.Main)

    override fun onCreate() {
        super.onCreate()

        // Initiate the notification service
        NotificationService.init(this)

        // Initiate the iAdvize SDK
        IAdvizeSDK.logLevel = IAdvizeSDKConfig.logLevel

        // Configure the targeting language (should match the user language in a real use case)
        IAdvizeSDK.targetingController.language = IAdvizeSDKConfig.targetingLanguage

        // Configure iAdvize Chatbox
        IAdvizeSDK.chatboxController.setupChatbox(IAdvizeSDKConfig.chatboxConfiguration(this))

        // Initiate then activate the iAdvize SDK (start a user session) using the coroutine APIs.
        scope.launch {
            IAdvizeSDK.initiateAsync(this@App)

            try {
                IAdvizeSDK.activateAsync(
                    IAdvizeSDKConfig.projectId,
                    IAdvizeSDKConfig.authOption,
                    IAdvizeSDKConfig.gdprOption,
                )
                Toast.makeText(baseContext, "🎉 iAdvize SDK Activation success", Toast.LENGTH_SHORT)
                    .show()

                // Activate the targeting rule
                IAdvizeSDK.targetingController.activateTargetingRule(IAdvizeSDKConfig.targetingRule)
            } catch (error: IAdvizeSDK.Error) {
                Toast.makeText(baseContext, "❌ iAdvize SDK Activation failure", Toast.LENGTH_SHORT)
                    .show()
            }
        }
    }
}
