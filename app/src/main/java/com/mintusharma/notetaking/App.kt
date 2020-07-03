package com.mintusharma.notetaking

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import android.graphics.Color
import android.os.Build
import androidx.core.content.ContextCompat
import com.mintusharma.notetaking.data.NoteAndRemindersDB
import com.mintusharma.notetaking.repositories.Repository
import com.mintusharma.notetaking.viewmodel.HomeViewPagerViewModel
import com.mintusharma.notetaking.viewmodel.NotesDisplayViewModel
import com.mintusharma.notetaking.viewmodel.NotesEditViewModel
import com.mintusharma.notetaking.viewmodel.ReminderEditViewModel
import com.mintusharma.notetaking.viewmodel.NotesListViewModel
import com.mintusharma.notetaking.viewmodel.RemindersListViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.context.startKoin
import org.koin.dsl.module

class App : Application() {
    private val koinModule = module {
        single {
            //Database
            NoteAndRemindersDB.getInstance(androidContext())
        }
        single {
            val db: NoteAndRemindersDB = get()
            Repository(db.notesDao, db.remindersDao, androidContext())
        }

        viewModel {
            HomeViewPagerViewModel(
                get()
            )
        }

        viewModel {
            NotesListViewModel(
                get(),
                this@App
            )
        }
        viewModel { (noteId: String) ->
            NotesDisplayViewModel(
                get(),
                noteId
            )
        }
        viewModel { (noteId: String) ->
            NotesEditViewModel(
                get(),
                noteId,
                this@App
            )
        }
        viewModel { (reminderId: String) ->
            ReminderEditViewModel(
                get(),
                reminderId,
                this@App
            )
        }
        viewModel {
            RemindersListViewModel(
                get(),
                this@App
            )
        }

    }


    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger()
            androidContext(this@App)
            modules(koinModule)
        }
        createNotificationChannel(getString(R.string.reminder_notification_channel_id),
            getString(R.string.reminder_notification_channel_name),
            getString(R.string.reminder_notification_channel_description))
    }

    fun createNotificationChannel(channelId: String, channelName: String,description:String){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val notificationChannel = NotificationChannel(
                channelId,
                channelName,
                NotificationManager.IMPORTANCE_HIGH)
            notificationChannel.apply {
                enableLights(true)
                lightColor = Color.BLUE
                enableVibration(true)
            }
            notificationChannel.description = description
            val notificationManager = ContextCompat.getSystemService(this.applicationContext,
                NotificationManager::class.java
            ) as NotificationManager
            notificationManager.createNotificationChannel(notificationChannel)
        }
    }


}

