package com.mintusharma.notetaking.viewmodel

import android.app.AlarmManager
import android.app.Application
import android.content.Context
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.Transformations
import androidx.lifecycle.viewModelScope
import com.mintusharma.notetaking.data.Reminders
import com.mintusharma.notetaking.repositories.Repository
import com.mintusharma.notetaking.util.AlarmUtil
import kotlinx.coroutines.launch

class RemindersListViewModel(
    private val repository: Repository,
    private val app: Application
) : AndroidViewModel(app) {

    companion object {
        private const val TAG = "RemindersListViewModel"
    }
    private val alarmManager = app.getSystemService(Context.ALARM_SERVICE) as AlarmManager
    val reminders = repository.getAllReminders()
    val isEmpty = Transformations.map(reminders) {
        it.isEmpty()
    }

    fun updateReminder(reminder: Reminders) {
        viewModelScope.launch {
            repository.update(reminder)
        }
    }

    fun updateAlarm(reminder: Reminders) {
            if(reminder.isActive){
                createReminderAlarm(reminder)
            }else{
                cancelExistingAlarm(reminder)
            }
    }

  private  fun createReminderAlarm(reminder: Reminders) {
          AlarmUtil.createAlarm(
              app.applicationContext,
              reminder,
              alarmManager
          )
      Log.d(TAG, "createReminderAlarm: ${reminder.id}")
    }

  private  fun cancelExistingAlarm(reminder: Reminders) {
        AlarmUtil.cancelAlarm(
            app.applicationContext,
            reminder,
            alarmManager
        )
      Log.d(TAG, "cancelExistingAlarm: ${reminder.id}")
    }

}