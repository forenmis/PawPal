package com.example.pawpal.data.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.pawpal.screens.home.medicine.entity.Note

@Entity
data class NotesDatabase(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val notificationId: Long,
    val title: String
)

fun NotesDatabase.toNote(): Note {
    return Note(
        id = this.id,
        notificationId = this.notificationId,
        title = this.title
    )
}

fun Note.toNoteDatabase(): NotesDatabase {
    return NotesDatabase(
        id = this.id,
        notificationId = this.notificationId,
        title = this.title
    )
}

