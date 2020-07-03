package com.mintusharma.notetaking.viewmodel

import androidx.lifecycle.*
import com.mintusharma.notetaking.data.Notes
import com.mintusharma.notetaking.repositories.Repository
import com.mintusharma.notetaking.util.Destination
import com.mintusharma.notetaking.util.Event

class NotesDisplayViewModel(
    private val repository: Repository,
    private val noteIdentifier: String
) : ViewModel() {

    val note: LiveData<Notes> = repository.getNoteLiveDataById(noteIdentifier)



    private val _navigationEvent = MutableLiveData<Event<Destination>>()
    val navigationEvent: LiveData<Event<Destination>>
        get() = _navigationEvent


    fun navigateToEdit() {
        _navigationEvent.value = Event(Destination.EDITFRAGMENT)
    }

    fun navigateUp() {
        _navigationEvent.value = Event(Destination.UP)
    }

}