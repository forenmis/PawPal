package com.example.pawpal.data.database.entity

import androidx.room.Embedded
import androidx.room.Relation

data class NotificationWithNotesDatabase(
    @Embedded
    val petNotificationDatabase: PetNotificationDatabase,
    @Relation(
        parentColumn = "id",
        entityColumn = "notificationId"
    )
    val notes: List<NotesDatabase>
)