package com.udacity.project4.locationreminders.domain.usecases.loadReminders

import com.udacity.project4.locationreminders.framework.model.ReminderDataItem
import com.udacity.project4.locationreminders.framework.model.Result

interface LoadRemindersUseCase {

    suspend fun getReminders(): List<ReminderDataItem>
}