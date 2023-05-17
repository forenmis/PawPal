package com.example.pawpal.utils

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Bundle
import com.example.pawpal.app.receiver.Receiver.Companion.ACTION
import com.example.pawpal.app.receiver.Receiver.Companion.TITLE_KEY
import com.example.pawpal.screens.home.medicine.entity.PetNotification
import java.util.concurrent.TimeUnit

object AlarmHelper {

    fun createTask(context: Context, petNotification: PetNotification) {
        val alarmManager =
            context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val intent = Intent()
        intent.action = ACTION
        val bundle = Bundle()
        bundle.putString(TITLE_KEY, petNotification.title)
        intent.putExtras(bundle)
        val pendingIntent = PendingIntent.getBroadcast(
            context,
            3,
            intent,
            PendingIntent.FLAG_IMMUTABLE
        )
        alarmManager.setExact(
            AlarmManager.RTC_WAKEUP,
            calculateTimeStamp(petNotification),
            pendingIntent
        )
    }

    private fun calculateTimeStamp(petNotification: PetNotification): Long {
        return petNotification.date.time - TimeUnit.MINUTES.toMillis(petNotification.remindIn.toLong())
    }
}