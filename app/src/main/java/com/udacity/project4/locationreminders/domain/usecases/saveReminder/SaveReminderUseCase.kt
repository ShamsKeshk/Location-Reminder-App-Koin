package com.udacity.project4.locationreminders.domain.usecases.saveReminder

import com.udacity.project4.locationreminders.framework.model.ReminderDataItem

interface SaveReminderUseCase {

    suspend fun saveReminder(reminderDataItem: ReminderDataItem)
}