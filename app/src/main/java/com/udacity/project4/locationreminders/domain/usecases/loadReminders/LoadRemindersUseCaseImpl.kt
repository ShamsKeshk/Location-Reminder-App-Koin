package com.udacity.project4.locationreminders.domain.usecases.loadReminders

import com.udacity.project4.locationreminders.framework.repo.ReminderRepository
import com.udacity.project4.locationreminders.framework.model.ReminderDataItem

class LoadRemindersUseCaseImpl(val reminderRepository: ReminderRepository): LoadRemindersUseCase {

    override suspend fun getReminders(): List<ReminderDataItem> {
        return reminderRepository.getReminders()
    }
}