package com.iadvize.conversation.sdk.demo.feature.notifications

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.NotificationManager.IMPORTANCE_HIGH
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.content.Intent.FLAG_ACTIVITY_CLEAR_TOP
import android.media.RingtoneManager
import android.media.RingtoneManager.TYPE_NOTIFICATION
import android.os.Build.VERSION.SDK_INT
import android.os.Build.VERSION_CODES.O
import androidx.core.app.NotificationCompat
import androidx.navigation.NavDeepLinkBuilder
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import com.iadvize.conversation.sdk.IAdvizeSDK
import com.iadvize.conversation.sdk.demo.R
import com.iadvize.conversation.sdk.demo.feature.root.RootActivity

class NotificationService : FirebaseMessagingService() {
    companion object {
        private const val CHANNEL_ID = "sample-channel-id"

        fun init(context: Context) {
            if (SDK_INT >= O) {
                notificationManager(context).createNotificationChannel(
                    NotificationChannel(
                        CHANNEL_ID,
                        CHANNEL_ID,
                        IMPORTANCE_HIGH
                    )
                )
            }
        }

        private fun notificationManager(context: Context) =
            context.getSystemService(NOTIFICATION_SERVICE) as NotificationManager
    }

    /**
     * This is called when the user's FCM InstanceID token is refreshed.
     * Here you should associate this token with any server-side account maintained by your
     * application, as well as the iAdvize SDK.
     *
     * @param token The new token.
     */
    override fun onNewToken(token: String) {
        super.onNewToken(token)

        // Register this token to your other server dependencies
        // ...

        // Update the token in the iAdvize SDK
        IAdvizeSDK.notificationController.registerPushToken(token)
    }

    /**
     * Called when message is received.
     *
     * @param remoteMessage Object representing the message received from Firebase Cloud Messaging.
     */
    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        if (IAdvizeSDK.notificationController.isIAdvizePushNotification(remoteMessage.data)
            && !IAdvizeSDK.chatboxController.isChatboxPresented()
        ) {
            val content = remoteMessage.data["content"]
                ?: baseContext.resources.getString(R.string.iadvize_conversation_attachment_operator)
            showNotification(content)
        }
    }

    /**
     * Create and show a simple notification.
     */
    private fun showNotification(text: String) {
        val intent = Intent(this, RootActivity::class.java)
        intent.addFlags(FLAG_ACTIVITY_CLEAR_TOP)
        val pendingIntent: PendingIntent = NavDeepLinkBuilder(this)
            .setGraph(R.navigation.nav_graph)
            .setDestination(R.id.ProductListFragment)
            .createPendingIntent()

        val defaultSoundUri = RingtoneManager.getDefaultUri(TYPE_NOTIFICATION)
        val notificationBuilder = NotificationCompat.Builder(this, CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_notification)
            .setColor(resources.getColor(R.color.outer_space))
            .setContentTitle(getString(R.string.app_name))
            .setContentText(text)
            .setAutoCancel(true)
            .setSound(defaultSoundUri)
            .setContentIntent(pendingIntent)

        notificationManager(this).notify(0, notificationBuilder.build())
    }
}
