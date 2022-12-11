package com.udacity.project4.di

import android.content.Context
import androidx.room.Room
import com.udacity.project4.locationreminders.framework.local.dataSource.LocalReminderDataSource
import com.udacity.project4.locationreminders.framework.local.dataSource.LocalReminderDataSourceImpl
import com.udacity.project4.locationreminders.framework.local.database.RemindersDatabase
import com.udacity.project4.locationreminders.framework.repo.ReminderRepository
import com.udacity.project4.locationreminders.framework.repo.RemindersRepositoryImpl
import org.koin.core.qualifier.named
import org.koin.dsl.module


class LocalDatabaseTestingModule {

    companion object{

        fun provideLocalDatabaseModule(): org.koin.core.module.Module{
            return module {
                single () {
                    RemindersDatabase.getInstance(get() as Context)
                }

                single<ReminderRepository>()  {
                    return@single  RemindersRepositoryImpl(get(),get())
                }

                single(named("testing"))  {
                    Room.inMemoryDatabaseBuilder(
                        get(),
                        RemindersDatabase::class.java)
                        .allowMainThreadQueries()
                        .build()
                }

                single<LocalReminderDataSource>()  {
                    return@single  LocalReminderDataSourceImpl(get(named("testing")))
                }
            }
        }
    }
}