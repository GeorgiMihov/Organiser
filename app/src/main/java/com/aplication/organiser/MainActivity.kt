package com.aplication.organiser

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.aplication.organiser.viewmodel.NoteViewModel

class MainActivity : AppCompatActivity() {
    private lateinit var noteViewModel: NoteViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setUpViewModel()
        setViewModelObserver()
    }

    private fun setViewModelObserver() {
        noteViewModel.getAllNotes().observe(this, Observer { allNotes ->
            //TODO: add changes to recycler view that is to be implemented

        })
    }

    private fun setUpViewModel() {
        noteViewModel = ViewModelProvider(
            this,
            ViewModelProvider.AndroidViewModelFactory.getInstance(this.application)
        ).get(
            NoteViewModel::class.java
        )
    }
}