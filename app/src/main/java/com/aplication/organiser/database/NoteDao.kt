package com.aplication.organiser.database

import androidx.lifecycle.LiveData
import androidx.room.*
import com.aplication.organiser.database.NoteDbModel

/**
 * Dao is an abbreviation for Data access object. The class is used to define methods to interact with the database
 */
@Dao
interface NoteDao {

    @Insert
    fun insert(note: NoteDbModel)

    @Update
    fun update(note: NoteDbModel)

    @Delete
    fun delete(note: NoteDbModel)

    @Query("DELETE FROM note_table")
    fun deleteAllNotes()

    @Query("SELECT * FROM note_table ORDER BY priority DESC")
    fun getAllNotes(): LiveData<List<NoteDbModel>>

}