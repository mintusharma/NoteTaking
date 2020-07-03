package com.mintusharma.notetaking.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.mintusharma.notetaking.repositories.Repository
import com.mintusharma.notetaking.util.Event

class HomeViewPagerViewModel(private val repository: Repository) : ViewModel() {

    private val _fabNavListenner= MutableLiveData<Event<Unit>>()
    val fabNavListenner:LiveData<Event<Unit>>
    get() = _fabNavListenner


    fun fabNavTriggered(){
        _fabNavListenner.value = Event(Unit)
    }

     /*fun insert(note: Notes){
        viewModelScope.launch {
            repository.insert(note)
        }
    }*/




}