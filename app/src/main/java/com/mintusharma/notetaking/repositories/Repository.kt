package com.mintusharma.notetaking.repositories

import android.content.Context
import com.mintusharma.notetaking.data.Notes
import com.mintusharma.notetaking.data.Reminders
import com.mintusharma.notetaking.data.NotesDao
import com.mintusharma.notetaking.data.RemindersDao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class Repository(
    private val noteDao: NotesDao,
    private val reminderDao: RemindersDao,
    private val context: Context
) {

    private val filesDir = context.applicationContext.filesDir

    fun getAllNotes() = noteDao.getNotes()
    fun getFavoriteNotes() = noteDao.getFavoriteNotes()
    fun getNoteLiveDataById(noteId: String) = noteDao.getNoteLiveDataById(noteId)

    suspend fun delete(note: Notes) {
        withContext(Dispatchers.IO) {
            noteDao.delete(note)
        }
    }

    suspend fun clear() {
        withContext(Dispatchers.IO) {
            noteDao.clearAllNotes()
        }
    }

    suspend fun update(note: Notes) {
        withContext(Dispatchers.IO) {
            noteDao.updateNote(note)
        }
    }

    suspend fun insert(note: Notes) {
        withContext(Dispatchers.IO) {
            noteDao.insertNote(note)
        }
    }

    suspend fun getNoteById(noteId: String): Notes? {
        return withContext(Dispatchers.IO) {
            noteDao.getNoteById(noteId)
        }
    }

    fun getAllReminders() = reminderDao.getReminders()

    suspend fun getRemindersList():List<Reminders>?{
        return withContext(Dispatchers.IO){
            reminderDao.getRemindersList()
        }
    }

    suspend fun getReminderById(reminderId: String): Reminders? {
        return withContext(Dispatchers.IO) {
            reminderDao.getReminderById(reminderId)
        }
    }

    suspend fun getLatestReminder():Reminders?{
        return  withContext(Dispatchers.IO){
            reminderDao.getLatestReminder()
        }
    }

    suspend fun insert(reminder: Reminders){
        withContext(Dispatchers.IO) {
            reminderDao.insertReminder(reminder)
        }
    }

    suspend fun update(reminder: Reminders) {
        withContext(Dispatchers.IO) {
            reminderDao.updateReminder(reminder)
        }
    }
    suspend fun delete(reminder: Reminders) {
        withContext(Dispatchers.IO) {
            reminderDao.delete(reminder)
        }
    }
}