package com.udacity.project4.locationreminders.framework.repo

import com.udacity.project4.locationreminders.framework.model.ReminderDataItem
import com.udacity.project4.locationreminders.framework.model.Result

class FakeRemindersRepository: ReminderRepository {

    private val remindersData = mutableListOf<ReminderDataItem>()

    override suspend fun getReminders(): List<ReminderDataItem> {
        return remindersData
    }

    override suspend fun saveReminder(reminder: ReminderDataItem) {
        remindersData.add(reminder)
    }

    override suspend fun getReminder(id: String): Result<ReminderDataItem?> {
       val item = remindersData.find {
           it.id == id
       }

        return Result.Success(item)
    }

    override suspend fun deleteAllReminders() {
        remindersData.clear()
    }
}