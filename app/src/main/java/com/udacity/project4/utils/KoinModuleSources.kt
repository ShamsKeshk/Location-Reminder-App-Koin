package com.udacity.project4.utils

import com.udacity.project4.locationreminders.domain.di.ReminderUseCasesModule
import com.udacity.project4.locationreminders.framework.di.FrameworkModule
import com.udacity.project4.locationreminders.framework.local.di.LocalDatabaseModule
import org.koin.core.module.Module

fun getKoinModules(): List<Module>{
    return mutableListOf<Module>().apply {
        add(LocalDatabaseModule.provideLocalDatabaseModule())
        add(FrameworkModule.provideFrameworkModule())
        add(ReminderUseCasesModule.provideUsesCasesModule())
        add(ReminderUseCasesModule.provideViewModelsModule())
    }
}