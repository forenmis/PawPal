package com.example.pawpal.data.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.pawpal.screens.home.medicine.entity.PetNotification
import com.example.pawpal.utils.getDateInStr
import com.example.pawpal.utils.getFullDate
import com.example.pawpal.utils.getTimeInStr

@Entity(tableName = "notifications")
data class PetNotificationDatabase(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val title: String,
    val date: String,
    val time: String,
    val isPeriod: Boolean,
    val periodDays: Int?,
    val remindIn: Int
)

fun PetNotification.toNotificationDatabase(): PetNotificationDatabase {
    return PetNotificationDatabase(
        id = id,
        title = title,
        date = date.getDateInStr(),
        time = date.getTimeInStr(),
        isPeriod = isPeriod,
        periodDays = periodDays,
        remindIn = remindIn
    )
}

fun PetNotificationDatabase.toNotification(): PetNotification {
    return PetNotification(
        id = id,
        title = title,
        date = getFullDate(date, time),
        isPeriod = isPeriod,
        periodDays = periodDays,
        remindIn = remindIn
    )
}
