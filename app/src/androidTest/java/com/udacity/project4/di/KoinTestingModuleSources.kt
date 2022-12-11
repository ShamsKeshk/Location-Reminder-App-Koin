package com.udacity.project4.di

import org.koin.core.module.Module

fun getKoinTestingModules(): List<Module>{
    return mutableListOf<Module>().apply {
        add(LocalDatabaseTestingModule.provideLocalDatabaseModule())
        add(FrameworkTestingModule.provideFrameworkModule())
        add(ReminderUseCasesTestingModule.provideUsesCasesModule())
        add(ReminderUseCasesTestingModule.provideViewModelsModule())
    }
}