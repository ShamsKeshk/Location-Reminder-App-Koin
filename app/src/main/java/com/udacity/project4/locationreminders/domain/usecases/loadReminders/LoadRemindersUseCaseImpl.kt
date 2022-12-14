package com.udacity.project4.locationreminders.domain.usecases.loadReminders

import com.udacity.project4.locationreminders.framework.repo.ReminderRepository
import com.udacity.project4.locationreminders.framework.model.ReminderDataItem
import com.udacity.project4.locationreminders.framework.model.Result

//Tested Correctly
class LoadRemindersUseCaseImpl constructor(val reminderRepository: ReminderRepository): LoadRemindersUseCase {

    override suspend fun getReminders(): Result<List<ReminderDataItem>> {
        return reminderRepository.getReminders()
    }
}