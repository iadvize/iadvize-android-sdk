package com.iadvize.conversation.sdk.demo

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import androidx.multidex.MultiDexApplication
import com.iadvize.conversation.sdk.IAdvizeManager
import com.iadvize.conversation.sdk.demo.models.Catalog
import com.iadvize.conversation.sdk.logger.Logger

/**
 * Created by Yann Coupé on 20/08/2018.
 * Copyright © 2018 iAdvize. All rights reserved.
 */
class App : MultiDexApplication() {

    override fun onCreate() {
        super.onCreate()

        // Initialize notification manager for version under Oreo
        val notificationManager = applicationContext.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            notificationManager.createNotificationChannel(NotificationChannel("sample-channel-id", "Sample", NotificationManager.IMPORTANCE_HIGH))
        }

        // Choose the log level of the SDK from `VERBOSE` to `SUCCESS`.
        IAdvizeManager.logLevel = Logger.Level.VERBOSE

        // Register your application ID.
        // This information is available on your app on the iAdvize Administration website.
        IAdvizeManager.registerApplicationId(this, "your-application-id")

        Catalog.construct(this)
    }
}