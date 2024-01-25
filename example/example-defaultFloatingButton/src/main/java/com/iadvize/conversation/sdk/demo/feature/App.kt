package com.iadvize.conversation.sdk.demo.feature

import androidx.multidex.MultiDexApplication
import com.iadvize.conversation.sdk.demo.feature.notifications.NotificationService

class App : MultiDexApplication() {
    override fun onCreate() {
        super.onCreate()

        // Initiate the notification service
        NotificationService.init(this)

        // Setup the iAdvize SDK
        IAdvizeSDKConfig.setup(this)
    }
}
