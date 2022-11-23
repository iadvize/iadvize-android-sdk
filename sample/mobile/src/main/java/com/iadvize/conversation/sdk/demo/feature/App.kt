package com.iadvize.conversation.sdk.demo.feature

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
    }
}
