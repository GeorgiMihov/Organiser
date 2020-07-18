package com.aplication.organiser.repository

import android.app.Application
import androidx.lifecycle.LiveData
import com.aplication.organiser.database.NoteDao
import com.aplication.organiser.database.NoteDatabase
import com.aplication.organiser.database.NoteDbModel

class NoteRepository(application: Application) {
    private val noteDao: NoteDao
    private val allNotes: LiveData<List<NoteDbModel>>

    init {
        val database = NoteDatabase.getInstance(application)

        noteDao = database.noteDao()
        allNotes = noteDao.getAllNotes()
    }

    //All of these function need to be called with a coroutine
    suspend fun insert(note: NoteDbModel) = noteDao.insert(note)

    suspend fun update(note: NoteDbModel) = noteDao.update(note)

    suspend fun delete(note: NoteDbModel) = noteDao.delete(note)

    suspend fun deleteAllNotes() = noteDao.deleteAllNotes()

    fun getAllNotes(): LiveData<List<NoteDbModel>> {
        return allNotes
    }
}