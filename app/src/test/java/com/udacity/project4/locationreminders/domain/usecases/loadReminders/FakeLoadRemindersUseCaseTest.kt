package com.udacity.project4.locationreminders.domain.usecases.loadReminders

import com.udacity.project4.locationreminders.framework.model.ReminderDataItem
import com.udacity.project4.locationreminders.framework.model.Result

class FakeLoadRemindersUseCaseTest: LoadRemindersUseCase {

    private val remindersData = mutableListOf<ReminderDataItem>()

    override suspend fun getReminders(): List<ReminderDataItem> {
        return remindersData
    }
}