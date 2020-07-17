package com.aplication.organiser.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = DbConstants.NOTE_ENTITY_NAME)
data class NoteDbModel(
    var title: String,
    var description: String,
    var priority: Int
) {
    @PrimaryKey(autoGenerate = true)
    var id: Int = DbConstants.NOTE_ID_DEFAULT_VALUE
}