package com.mintusharma.notetaking.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.*
import com.mintusharma.notetaking.data.Notes
import com.mintusharma.notetaking.repositories.Repository
import com.mintusharma.notetaking.util.Filter
import kotlinx.coroutines.launch


class NotesListViewModel(private val repository: Repository, val app: Application) :
    AndroidViewModel(app) {
    companion object {
        private const val TAG = "NotesListViewModel"
    }
    
    private val _itemPosition = MutableLiveData<Int>(getInitialPosition())
    private fun getInitialPosition(): Int = 0

    private val _filter = Transformations.map(_itemPosition) { position ->

        when (position) {
            0 -> {
                Filter.ALL
            }
            1 -> {
                Filter.FAVORITES
            }
            else -> {
                Filter.ALL
            }
        }
    }
    val notes = Transformations.switchMap(_filter) { filterValue ->
        when (filterValue) {
            Filter.ALL -> {
                repository.getAllNotes()
            }
            Filter.FAVORITES -> {
                repository.getFavoriteNotes()
            }
            else -> {
                repository.getAllNotes()
            }
        }

    }

    val isEmpty = Transformations.map(notes) {
        it.isEmpty()
    }


    fun updateNote(note: Notes) {
        viewModelScope.launch {
            repository.update(note)
            Log.d(TAG, "updateNote: ")
        }
    }

}