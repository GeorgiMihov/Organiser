package com.aplication.organiser.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.aplication.organiser.database.NoteDbModel
import com.aplication.organiser.repository.NoteRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class AddNoteActivityViewModel(application: Application) : AndroidViewModel(application) {
    private val repository: NoteRepository = NoteRepository(application)

    fun insert(note: NoteDbModel) = viewModelScope.launch(Dispatchers.IO) {
        repository.insert(note)
    }

    fun update(note: NoteDbModel) = viewModelScope.launch(Dispatchers.IO) {
        repository.update(note)
    }

}