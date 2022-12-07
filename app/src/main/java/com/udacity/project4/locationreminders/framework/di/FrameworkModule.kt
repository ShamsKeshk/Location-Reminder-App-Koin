package com.udacity.project4.locationreminders.framework.di

import com.udacity.project4.locationreminders.framework.local.dataSource.LocalReminderDataSource
import com.udacity.project4.locationreminders.framework.local.dataSource.LocalReminderDataSourceImpl
import com.udacity.project4.locationreminders.framework.repo.ReminderRepository
import com.udacity.project4.locationreminders.framework.repo.RemindersRepositoryImpl
import com.udacity.project4.locationreminders.framework.local.database.RemindersDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class FrameworkModule {

    @Provides
    @Singleton
    fun provideCoroutineDispatcher(): CoroutineDispatcher {
        return Dispatchers.IO
    }

    @Provides
    @Singleton
    fun provideRemindersRepository(localReminderDataSource: LocalReminderDataSourceImpl): ReminderRepository {
        return RemindersRepositoryImpl(localReminderDataSource,Dispatchers.IO)
    }
}