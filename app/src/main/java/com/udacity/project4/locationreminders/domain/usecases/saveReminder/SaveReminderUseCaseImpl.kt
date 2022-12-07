package com.udacity.project4.locationreminders.domain.usecases.saveReminder

import com.udacity.project4.locationreminders.framework.repo.ReminderRepository
import com.udacity.project4.locationreminders.framework.model.ReminderDataItem
import javax.inject.Inject

class SaveReminderUseCaseImpl @Inject constructor(private val reminderRepository: ReminderRepository): SaveReminderUseCase {

    /**
     * Save the reminder to the data source
     */
    override suspend fun saveReminder(reminderDataItem: ReminderDataItem) {
        reminderRepository.saveReminder(reminderDataItem.asEntity())
    }
}