package com.udacity.project4.locationreminders.framework.local.di

import android.content.Context
import com.udacity.project4.locationreminders.framework.local.dataSource.LocalReminderDataSource
import com.udacity.project4.locationreminders.framework.local.dataSource.LocalReminderDataSourceImpl
import com.udacity.project4.locationreminders.framework.local.database.RemindersDao
import com.udacity.project4.locationreminders.framework.local.database.RemindersDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class LocalDatabaseModule {

    @Provides
    fun provideRemindersDatabaseDatabase(@ApplicationContext context: Context): RemindersDatabase {
        return RemindersDatabase.getInstance(context)
    }

    @Provides
    @Singleton
    fun provideRemindersDao(asteroidsDatabase: RemindersDatabase): RemindersDao {
        return asteroidsDatabase.reminderDao()
    }

    @Provides
    @Singleton
    fun provideLocalReminderDataSourceImpl(remindersDao: RemindersDao): LocalReminderDataSource {
        return LocalReminderDataSourceImpl(remindersDao)
    }
}