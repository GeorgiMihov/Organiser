package com.aplication.organiser.view

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.aplication.organiser.DomainConstants
import com.aplication.organiser.R
import com.aplication.organiser.viewmodel.MainActivityViewModel
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar

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

        setUpSwipeToDelete()
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

    private fun setUpSwipeToDelete() {
        ItemTouchHelper(
            object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT) {
                override fun onMove(
                    recyclerView: RecyclerView,
                    viewHolder: RecyclerView.ViewHolder,
                    target: RecyclerView.ViewHolder
                ): Boolean {
                    return false // true if moved, false otherwise
                }

                override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                    val position = viewHolder.adapterPosition
                    val noteToDelete = noteAdapter.getNoteAt(position)

                    mainActivityViewModel.delete(noteToDelete)
                    showSnackbar(DomainConstants.NOTE_DELETED)
                }
            }).attachToRecyclerView(recyclerView)
    }

    private fun showSnackbar(message: String) {
        val snackbar = Snackbar.make(findViewById(R.id.main_activity_view_root), message, Snackbar.LENGTH_SHORT)
        snackbar.show()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when(item.itemId) {

            R.id.delete_all_notes -> {
                mainActivityViewModel.deleteAllNotes()
                showSnackbar(DomainConstants.ALL_NOTES_DELETED)
                false
            }

            else -> super.onOptionsItemSelected(item)
        }
    }
}