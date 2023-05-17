package com.example.pawpal.app.receiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.example.pawpal.utils.NotificationHelper

class Receiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        val bundle = intent.extras!!
        val title = bundle.getString(TITLE_KEY) ?: "title"
        NotificationHelper.showNotification(context, title)
    }

    companion object {
        const val ACTION = "com.example.pawpal.Receiver"
        const val TITLE_KEY = "TITLE_KEY"
    }
}