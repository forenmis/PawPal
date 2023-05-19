package com.example.pawpal.data.database

import android.content.Context
import androidx.room.Room
import com.example.pawpal.data.database.entity.toNote
import com.example.pawpal.data.database.entity.toNoteDatabase
import com.example.pawpal.data.database.entity.toNotification
import com.example.pawpal.data.database.entity.toNotificationDatabase
import com.example.pawpal.screens.home.medicine.entity.Note
import com.example.pawpal.screens.home.medicine.entity.PetNotification
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single

class DatabaseManager(context: Context) {
    private val database = Room.databaseBuilder(
        context,
        AppDatabase::class.java,
        "notifications"
    ).build()

    fun getAllNotifications(): Single<List<PetNotification>> {
        return database.notificationDao().getAllNotifications()
            .map { list -> list.map { it.toNotification() } }
    }

    fun addPetNotification(notification: PetNotification): Completable {
        return database.notificationDao().addNotification(notification.toNotificationDatabase())
    }

    fun addNote(note: Note): Completable {
        return database.notificationDao().addNote(note.toNoteDatabase())
    }

    fun getNotificationById(id: Long): Single<Pair<PetNotification, List<Note>>> {
        return database.notificationDao().getNotificationById(id).map { notificationsWithNotes ->
            val notification = notificationsWithNotes.petNotificationDatabase.toNotification()
            val notes = notificationsWithNotes.notes.map { it.toNote() }
            notification to notes
        }
    }

    fun getAllNotes(): Single<List<Note>> {
        return database.notificationDao().getAllNotes().map { notes -> notes.map { it.toNote() } }
    }
}
