package com.udacity.project4.locationreminders.framework.local.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.udacity.project4.locationreminders.framework.local.data.ReminderDataEntity

/**
 * The Room Database that contains the reminders table.
 */
@Database(entities = [ReminderDataEntity::class], version = 1, exportSchema = false)
abstract class RemindersDatabase : RoomDatabase() {

    companion object {

        private const val DATABASE_NAME = "locationReminder.db"

        @Volatile
        private var INSTANCE: RemindersDatabase? = null

        private fun create(context: Context) : RemindersDatabase =
            Room.databaseBuilder(context, RemindersDatabase::class.java,
                DATABASE_NAME
            )
                .fallbackToDestructiveMigration()
                .build()

        fun getInstance(context: Context): RemindersDatabase {
            synchronized(this){
                var instance = INSTANCE

                if (instance == null){
                    instance = create(context)
                }
                INSTANCE = instance
                return instance
            }
        }

    }


    abstract fun reminderDao(): RemindersDao
}