package com.aplication.organiser.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.EditText
import android.widget.NumberPicker
import androidx.lifecycle.ViewModelProvider
import com.aplication.organiser.DomainConstants
import com.aplication.organiser.R
import com.aplication.organiser.database.NoteDbModel
import com.aplication.organiser.viewmodel.AddNoteActivityViewModel
import com.google.android.material.snackbar.Snackbar

class AddNoteActivity : AppCompatActivity() {
    private lateinit var noteTitle: EditText
    private lateinit var description: EditText
    private lateinit var priority: NumberPicker

    private lateinit var addNoteActivityViewModel: AddNoteActivityViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_note)

        setUpUIElements()
        setNumberPickerBoundaries()
        setUpActionBar()

        setUpViewModel()
    }

    private fun setUpUIElements() {
        noteTitle = findViewById(R.id.edit_title)
        description = findViewById(R.id.edit_description)
        priority = findViewById(R.id.pick_priority)
    }

    private fun setNumberPickerBoundaries() {
        priority.minValue = DomainConstants.PRIORITY_MIN_VALUE
        priority.maxValue = DomainConstants.PRIORITY_MAX_VALUE
    }

    private fun setUpActionBar() {
        supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_close)
        title = DomainConstants.ADD_NOTE_ACTIVITY_TITLE
    }

    private fun setUpViewModel() {
        addNoteActivityViewModel = ViewModelProvider(
            this,
            ViewModelProvider.AndroidViewModelFactory.getInstance(this.application)
        ).get(
            AddNoteActivityViewModel::class.java
        )
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_add_note, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.save_note -> {
                saveNote()
                false
            }

            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun saveNote() {
        val title: String = noteTitle.text.toString()
        val description: String = description.text.toString()
        val priority: Int = priority.value

        if (isNoteComplete(title, description)) {
            val newNote = NoteDbModel(title, description, priority)
            addNoteActivityViewModel.insert(newNote)

            showSnackbar("Note created successfully")
        } else {
            showSnackbar("Please enter a title and description")
        }
    }

    private fun showSnackbar(message: String) {
        val snackbar = Snackbar.make(findViewById(R.id.add_note_root_view), message, Snackbar.LENGTH_SHORT)
        snackbar.show()
    }

    private fun isNoteComplete(title: String, description: String): Boolean {
        return (title.trim().isEmpty() || description.trim().isEmpty()).not()
    }
}