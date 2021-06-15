package com.iadvize.conversation.sdk.demo.notifications

/**
 * Created by Yann Coupé on 22/08/2018.
 * Copyright © 2018 iAdvize. All rights reserved.
 */
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.media.RingtoneManager
import android.os.Build
import android.util.Log
import androidx.core.app.NotificationCompat
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import com.iadvize.conversation.sdk.IAdvizeSDK
import com.iadvize.conversation.sdk.demo.R
import com.iadvize.conversation.sdk.demo.activities.MainActivity

/**
 * Created by Yann Coupé on 30/03/2018.
 * Copyright © 2017 iAdvize. All rights reserved.
 */
class MyFirebaseMessagingService : FirebaseMessagingService() {

    override fun onNewToken(token: String) {
        sendRegistrationToServer(token)
    }

    /**
     * Persist token to third-party servers.
     * <p>
     * Modify this method to associate the user's FCM InstanceID token with any server-side account
     * maintained by your application.
     *
     * @param token The new token.
     */
    private fun sendRegistrationToServer(token: String?) {
        token?.let {
            Log.d("iAdvize SDK Demo", "Registering push token $it.")
            // Register to push notifications
            IAdvizeSDK.notificationController.registerPushToken(it)
        }
    }

    /**
     * Called when message is received.
     *
     * @param remoteMessage Object representing the message received from Firebase Cloud Messaging.
     */
    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        if (IAdvizeSDK.notificationController.isIAdvizePushNotification(remoteMessage.data)) {
            sendNotification("New message from iAdvize.")
        } else {
            sendNotification("This is a notification.")
        }
    }

    /**
     * Create and show a simple notification.
     */
    private fun sendNotification(content: String) {
        val intent = Intent(this, MainActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        val pendingIntent = PendingIntent.getActivity(
            this, 0, intent,
            PendingIntent.FLAG_ONE_SHOT
        )

        val channelId = "Cook Toys"
        val defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
        val notificationBuilder = NotificationCompat.Builder(this, channelId)
            .setSmallIcon(R.mipmap.ic_launcher)
            .setColor(0xff00ff00.toInt())
            .setContentTitle("Cook Toys")
            .setContentText(content)
            .setAutoCancel(true)
            .setSound(defaultSoundUri)
            .setContentIntent(pendingIntent)

        val notificationManager =
            getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            notificationManager.createNotificationChannel(
                NotificationChannel(
                    channelId,
                    channelId,
                    NotificationManager.IMPORTANCE_DEFAULT
                )
            )
        }
        notificationManager.notify(0, notificationBuilder.build())
    }
}