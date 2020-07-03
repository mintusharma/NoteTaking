package com.mintusharma.notetaking.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.*
import com.mintusharma.notetaking.data.Notes
import com.mintusharma.notetaking.repositories.Repository
import kotlinx.coroutines.launch
import com.mintusharma.notetaking.R
import com.mintusharma.notetaking.util.*

class NotesEditViewModel(
    private val repository: Repository,
    private val noteIdentifier: String?,
    private val app: Application
) : AndroidViewModel(app) {


    companion object {
        private const val TAG = "NotesEditViewModel"
    }

    private lateinit var editableNote: Notes

    init {
        initializeNote()
    }


    val intent = captureImageIntent
    private val _snackBarEvent = MutableLiveData<Event<String>>()
    val snackBarEvent: LiveData<Event<String>>
        get() = _snackBarEvent
    private val _navigationEvent = MutableLiveData<Event<Destination>>()
    val navigationEvent: LiveData<Event<Destination>>
        get() = _navigationEvent

    private val _showDatePickerEvent = MutableLiveData<Event<String>>()
    val showDatePickerEvent: LiveData<Event<String>>
        get() = _showDatePickerEvent

    private val _showDeletDialogEvent = MutableLiveData<Event<String>>()
    val showDeleteDialogEvent: LiveData<Event<String>>
        get() = _showDeletDialogEvent


    val _titleEditText = MutableLiveData<String>()

    val _descriptionEditText = MutableLiveData<String>()

    val _favoriteCheckBox = MutableLiveData<Boolean>()

    val _dateTextView = MutableLiveData<String>()


    fun initializeNote() {
        viewModelScope.launch {
            if (noteIdentifier != null) {
                editableNote = repository.getNoteById(noteIdentifier) ?: Notes()
            } else {
                editableNote = Notes()
            }
            Log.d(TAG, "showlog: identifier is ${editableNote.noteIdentifier}")
            updateUI()
        }
    }

    fun updateUI() {
        _titleEditText.value = editableNote.title
        _descriptionEditText.value = editableNote.description
       _dateTextView.value = editableNote.date
    }

    override fun onCleared() {
        super.onCleared()
        showlog()
    }

    fun showlog() {
        Log.d(TAG, "showlog: title is ${_titleEditText.value}")
        Log.d(TAG, "showlog: desc is ${_descriptionEditText.value}")
       // Log.d(TAG, "showlog: isfav ${_favoriteCheckBox.value}")
        Log.d(TAG, "showlog: date is ${_dateTextView.value}")
        Log.d(TAG, "showlog: identifier is ${editableNote.noteIdentifier}")
    }

    fun saveNote() {
        if (_titleEditText.value.isNullOrEmpty() || _descriptionEditText.value.isNullOrEmpty()) {
           // _snackBarEvent.value = Event(app.getString(R.string.snackbartext_emptynote))
        } else {
            updateNote()
            viewModelScope.launch {
                if (noteIdentifier == null) {
                    repository.insert(editableNote)
                } else {
                    repository.update(editableNote)
                }
                _navigationEvent.value = Event(Destination.UP)
            }
        }
    }

    fun updateNote() {
        editableNote.apply {
            title = _titleEditText.value!!
            description = _descriptionEditText.value!!
            date = _dateTextView.value!!
        }

    }

    fun navigateUp() {
        _navigationEvent.value = Event(Destination.UP)
    }

    fun showDatePicker() {
        _showDatePickerEvent.value = Event(editableNote.date)
    }

    fun showDeleteDialog() {
        _showDeletDialogEvent.value = Event(app.getString(R.string.delete_note_message))
    }

    fun updateDateTextView(date: String) {
        _dateTextView.value = date
    }

    fun deleteAndNavigateToList() {
        viewModelScope.launch {
            repository.delete(editableNote)
            _navigationEvent.value = Event(Destination.VIEWPAGERFRAGMENT)
        }
    }

}