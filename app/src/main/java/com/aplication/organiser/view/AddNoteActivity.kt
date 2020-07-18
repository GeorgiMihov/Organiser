package com.aplication.organiser.view

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.EditText
import android.widget.NumberPicker
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.aplication.organiser.DomainConstants
import com.aplication.organiser.R
import com.aplication.organiser.database.NoteDbModel
import com.aplication.organiser.viewmodel.AddNoteActivityViewModel
import com.google.android.material.snackbar.Snackbar

/**
 * An activity that creates notes (or updates note if given parameters) and saves them to the database through view model.
 */

class AddNoteActivity : AppCompatActivity() {
    companion object {
        const val EXTRA_ID = "com.aplication.organiser.view.EXTRA_ID"
        const val EXTRA_TITLE = "com.aplication.organiser.view.EXTRA_TITLE"
        const val EXTRA_DESCRIPTION = "com.aplication.organiser.view.EXTRA_DESCRIPTION"
        const val EXTRA_PRIORITY = "com.aplication.organiser.view.EXTRA_PRIORITY"
    }

    private lateinit var noteTitle: EditText
    private lateinit var description: EditText
    private lateinit var priority: NumberPicker

    private lateinit var addNoteActivityViewModel: AddNoteActivityViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_note)

        setUpUIElements()
        setNumberPickerBoundaries()

        if (intent.hasExtra(EXTRA_ID)) {
            setUpActionBar(DomainConstants.EDIT_NOTE_ACTIVITY_TITLE)
            populateFields()
        } else {
            setUpActionBar(DomainConstants.ADD_NOTE_ACTIVITY_TITLE)
        }

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

    private fun setUpActionBar(activityTitle: String) {
        supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_close)
        title = activityTitle
    }

    private fun populateFields() {
        noteTitle.setText(intent.getStringExtra(EXTRA_TITLE))
        description.setText(intent.getStringExtra(EXTRA_DESCRIPTION))
        priority.setValue(intent.getIntExtra(EXTRA_PRIORITY, DomainConstants.PRIORITY_DEFAULT_VALUE))
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

        if (isCompleteNote(title, description)) {
            val newNote = NoteDbModel(title, description, priority)
            determineDatabaseAction(newNote)
        } else {
            showSnackbar(DomainConstants.INCOMPLETE_NOTE)
        }
    }

    private fun determineDatabaseAction(newNote: NoteDbModel) {
        if (intent.hasExtra(EXTRA_ID)) {
            newNote.id = intent.getIntExtra(EXTRA_ID, DomainConstants.INVALID_ID)
            addNoteActivityViewModel.update(newNote)

            showSnackbar(DomainConstants.NOTE_UPDATED)
        } else {
            addNoteActivityViewModel.insert(newNote)

            showSnackbar(DomainConstants.NOTE_CREATED)
        }
    }

    private fun showSnackbar(message: String) {
        val snackbar = Snackbar.make(findViewById(R.id.add_note_root_view), message, Snackbar.LENGTH_SHORT)
        snackbar.show()
    }

    private fun isCompleteNote(title: String, description: String): Boolean {
        return (title.trim().isEmpty() || description.trim().isEmpty()).not()
    }
}