package com.udacity.project4.locationreminders.domain.usecases.di

import com.udacity.project4.locationreminders.domain.usecases.loadReminders.LoadRemindersUseCase
import com.udacity.project4.locationreminders.domain.usecases.loadReminders.LoadRemindersUseCaseImpl
import com.udacity.project4.locationreminders.domain.usecases.saveReminder.SaveReminderUseCase
import com.udacity.project4.locationreminders.domain.usecases.saveReminder.SaveReminderUseCaseImpl
import com.udacity.project4.locationreminders.framework.repo.ReminderRepository
import com.udacity.project4.locationreminders.framework.repo.RemindersRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
class ReminderUseCasesModule {

    @Provides
    @Singleton
    fun provideSaveRemindersUsesCase(remindersRepositoryImpl: RemindersRepositoryImpl): SaveReminderUseCase {
        return SaveReminderUseCaseImpl(remindersRepositoryImpl)
    }

    @Provides
    @Singleton
    fun provideLoadRemindersUseCase(reminderRepository: ReminderRepository): LoadRemindersUseCase {
        return LoadRemindersUseCaseImpl(reminderRepository)
    }
}