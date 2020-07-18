package com.aplication.organiser.view

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.aplication.organiser.R
import com.aplication.organiser.viewmodel.MainActivityViewModel
import com.google.android.material.floatingactionbutton.FloatingActionButton

class MainActivity : AppCompatActivity() {
    private lateinit var mainActivityViewModel: MainActivityViewModel
    private lateinit var noteAdapter: NoteAdapter
    private lateinit var recyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setUpRecyclerView()
        setRecyclerViewAdapter()

        setUpFloatingActionButton()

        setUpViewModel()
        setViewModelObserver()
    }

    private fun setUpRecyclerView() {
        recyclerView = findViewById(R.id.note_recycler_view)

        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.setHasFixedSize(true)
    }

    private fun setRecyclerViewAdapter() {
        noteAdapter = NoteAdapter()
        recyclerView.adapter = noteAdapter
    }

    private fun setUpFloatingActionButton() {
        val floatingActionButton = findViewById<FloatingActionButton>(R.id.add_note)
        floatingActionButton.setOnClickListener { _ ->
            val intent = Intent(this, AddNoteActivity::class.java)
            startActivity(intent)
        }
    }

    private fun setViewModelObserver() {
        mainActivityViewModel.getAllNotes().observe(this, Observer { allNotes ->
            noteAdapter.setNotes(allNotes)
        })
    }

    private fun setUpViewModel() {
        mainActivityViewModel = ViewModelProvider(
            this,
            ViewModelProvider.AndroidViewModelFactory.getInstance(this.application)
        ).get(
            MainActivityViewModel::class.java
        )
    }
}