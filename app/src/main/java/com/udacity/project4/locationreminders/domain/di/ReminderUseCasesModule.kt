package com.udacity.project4.locationreminders.domain.di

import com.udacity.project4.locationreminders.domain.usecases.loadReminders.LoadRemindersUseCase
import com.udacity.project4.locationreminders.domain.usecases.loadReminders.LoadRemindersUseCaseImpl
import com.udacity.project4.locationreminders.domain.usecases.saveReminder.SaveReminderUseCase
import com.udacity.project4.locationreminders.domain.usecases.saveReminder.SaveReminderUseCaseImpl
import com.udacity.project4.locationreminders.domain.viewmodel.RemindersListViewModel
import com.udacity.project4.locationreminders.domain.viewmodel.SaveReminderViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

class ReminderUseCasesModule {

    companion object{

        fun provideUsesCasesModule(): org.koin.core.module.Module{
            return module {
                single<LoadRemindersUseCase> {
                    return@single LoadRemindersUseCaseImpl(get())
                }

                single<SaveReminderUseCase> {
                    return@single SaveReminderUseCaseImpl(get())
                }
            }
        }

        fun provideViewModelsModule(): org.koin.core.module.Module{
            return module {
                viewModel { RemindersListViewModel(get() as LoadRemindersUseCase) }
                viewModel { SaveReminderViewModel(get() as SaveReminderUseCase) }
            }
        }
    }
}