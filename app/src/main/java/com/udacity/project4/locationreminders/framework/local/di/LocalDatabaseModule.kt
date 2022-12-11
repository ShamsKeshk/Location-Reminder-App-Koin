package com.udacity.project4.locationreminders.framework.local.di

import android.content.Context
import com.udacity.project4.locationreminders.framework.local.dataSource.LocalReminderDataSource
import com.udacity.project4.locationreminders.framework.local.dataSource.LocalReminderDataSourceImpl
import com.udacity.project4.locationreminders.framework.local.database.RemindersDatabase
import org.koin.dsl.module


class LocalDatabaseModule {

    companion object{

        fun provideLocalDatabaseModule(): org.koin.core.module.Module{
            return module {
                single {
                    RemindersDatabase.getInstance(get() as Context)
                }

                single<LocalReminderDataSource> {
                    return@single  LocalReminderDataSourceImpl(get())
                }
            }
        }
    }
}