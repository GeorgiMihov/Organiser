package com.aplication.organiser.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.aplication.organiser.database.NoteDbModel
import com.aplication.organiser.repository.NoteRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainActivityViewModel(application: Application): AndroidViewModel(application) {
    private val repository: NoteRepository = NoteRepository(application)
    private var allNotes: LiveData<List<NoteDbModel>>

    init {
        allNotes = repository.getAllNotes()
    }

    fun delete(note: NoteDbModel) = viewModelScope.launch(Dispatchers.IO) {
        repository.delete(note)
    }

    fun deleteAllNotes() = viewModelScope.launch(Dispatchers.IO) {
        repository.deleteAllNotes()
    }

    fun getAllNotes(): LiveData<List<NoteDbModel>> {
        return allNotes
    }
}