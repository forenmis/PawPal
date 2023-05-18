package com.example.pawpal.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.pawpal.data.database.dao.PetNotificationDao
import com.example.pawpal.data.database.entity.NotesDatabase
import com.example.pawpal.data.database.entity.PetNotificationDatabase

@Database(
    entities = [
        PetNotificationDatabase::class,
        NotesDatabase::class
    ],
    version = 2
)
internal abstract class AppDatabase : RoomDatabase() {
    abstract fun notificationDao(): PetNotificationDao
}