package com.iadvize.conversation.sdk.demo.feature

import android.util.Log
import android.widget.Toast
import androidx.multidex.MultiDexApplication
import com.iadvize.conversation.sdk.IAdvizeSDK
import com.iadvize.conversation.sdk.demo.feature.notifications.NotificationService
import com.iadvize.conversation.sdk.feature.targeting.TargetingListener

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

        IAdvizeSDK.targetingController.listeners.add(object : TargetingListener {
            override fun onActiveTargetingRuleAvailabilityUpdated(isActiveTargetingRuleAvailable: Boolean) {
                Log.i(
                    "App",
                    "Targeting Rule is ${if (isActiveTargetingRuleAvailable) "" else "un"}available"
                )
            }

            override fun onActiveTargetingRuleAvailabilityUpdateFailed(error: IAdvizeSDK.Error) {
                when (error) {
                    is IAdvizeSDK.Error.NetworkUnreachableException -> {
                        Log.d(
                            "App",
                            "Rule could not be triggered because network was down. TargetingController will automatically retry to activate the rule."
                        )
                        // Nothing to do targeting will retry automatically after a delay
                    }

                    is IAdvizeSDK.Error.TargetingRuleTriggerException -> {
                        Log.d(
                            "App",
                            "Rule could not be triggered because of a technical error. TargetingController will automatically retry to activate the rule."
                        )
                        // Nothing to do targeting will retry automatically after a delay
                    }

                    is IAdvizeSDK.Error.NotActivatedException -> {
                        Log.d(
                            "App",
                            "Rule could not be triggered because the SDK is not yet activated, please call activate beforehand."
                        )
                        // ... Relaunch activation process
                    }

                    else -> {
                        Log.d(
                            "App",
                            "Rule could not be triggered because of an unknown error."
                        )
                        // ... Relaunch activation process
                    }
                }
            }
        })

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
