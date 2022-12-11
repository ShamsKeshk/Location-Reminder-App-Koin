package com.udacity.project4.di


import android.content.Context
import com.udacity.project4.locationreminders.domain.usecases.loadReminders.LoadRemindersUseCase
import com.udacity.project4.locationreminders.domain.usecases.saveReminder.SaveReminderUseCase
import com.udacity.project4.locationreminders.domain.usecases.saveReminder.SaveReminderUseCaseImpl
import com.udacity.project4.locationreminders.framework.local.dataSource.LocalReminderDataSource
import com.udacity.project4.locationreminders.framework.managers.GeofencingManager
import com.udacity.project4.locationreminders.framework.repo.RemindersRepositoryImpl
import com.udacity.project4.locationreminders.framework.managers.GeofencingManagerImpl
import com.udacity.project4.locationreminders.framework.repo.ReminderRepository
import kotlinx.coroutines.CoroutineDispatcher

import kotlinx.coroutines.Dispatchers
import org.koin.core.qualifier.named
import org.koin.dsl.module

class FrameworkTestingModule {

    companion object{

        fun provideFrameworkModule(): org.koin.core.module.Module{
            return module {
                single<CoroutineDispatcher>() {
                    return@single Dispatchers.IO
                }
                single<ReminderRepository>()  {
                    return@single  RemindersRepositoryImpl(get(),get())
                }
                single<GeofencingManager>()  {
                    return@single  GeofencingManagerImpl(get())
                }
            }
        }
    }
}