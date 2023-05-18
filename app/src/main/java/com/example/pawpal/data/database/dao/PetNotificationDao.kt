package com.example.pawpal.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import com.example.pawpal.data.database.entity.NotesDatabase
import com.example.pawpal.data.database.entity.NotificationWithNotesDatabase
import com.example.pawpal.data.database.entity.PetNotificationDatabase
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single

@Dao
interface PetNotificationDao {


    @Insert
    fun addNotification(notification: PetNotificationDatabase): Completable

    @Insert
    fun addNote(note : NotesDatabase):Completable

    @Query("SELECT * FROM notifications")
    fun getAllNotifications(): Single<List<PetNotificationDatabase>>

    @Query("DELETE FROM notifications WHERE id = :id")
    fun deleteById(id: Long): Completable

    @Transaction
    @Query("SELECT * FROM notifications WHERE id = :id")
    fun getById(id: Long): Single<NotificationWithNotesDatabase>
}