package com.example.pawpal.utils

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.core.app.NotificationCompat
import com.example.pawpal.screens.main.MainActivity

object NotificationHelper {
    fun showNotification(context: Context, title: String) {
        createNotificationChannel(context)
        val notification = createNotification(context, title)
        val notificationManager =
            context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.notify((Math.random() * 100).toInt(), notification)

    }

    private fun createNotificationChannel(context: Context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val notificationChannel =
                NotificationChannel("id", "name", NotificationManager.IMPORTANCE_DEFAULT)

            val notificationManager = context
                .getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(notificationChannel)
        }
    }

    private fun createNotification(context: Context, title: String): Notification {
        val intent = Intent(context, MainActivity::class.java)
        val pendingIntent =
            PendingIntent.getActivity(context, 2, intent, PendingIntent.FLAG_IMMUTABLE)

        return NotificationCompat.Builder(context, "id")
            .setContentTitle(title)
            .setSmallIcon(android.R.drawable.ic_dialog_email)
            .setAutoCancel(true)
            .setContentIntent(pendingIntent)
            .build()
    }
}