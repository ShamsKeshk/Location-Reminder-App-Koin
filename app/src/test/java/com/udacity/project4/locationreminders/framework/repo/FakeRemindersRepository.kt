package com.udacity.project4.locationreminders.framework.repo

import com.udacity.project4.locationreminders.framework.model.ReminderDataItem
import com.udacity.project4.locationreminders.framework.model.Result

class FakeRemindersRepository: ReminderRepository {

    var shouldReturnError = false
    var fakeGetRemindersErrorMessage = "Failed To getReminders"
    var fakeGetReminderErrorMessage = "Failed To getReminder"

    private val remindersData = mutableListOf<ReminderDataItem>()

    override suspend fun getReminders(): Result<List<ReminderDataItem>> {
        return when(shouldReturnError){
            true -> Result.Error(Throwable(fakeGetRemindersErrorMessage))
            else -> Result.Success(remindersData)
        }
    }

    override suspend fun saveReminder(reminder: ReminderDataItem) {
        remindersData.add(reminder)
    }

    override suspend fun getReminder(id: String): Result<ReminderDataItem?> {
       val item = remindersData.find {
           it.id == id
       }

        return when(shouldReturnError){
            true -> Result.Error(Throwable(fakeGetReminderErrorMessage))
            else -> Result.Success(item)
        }
    }

    override suspend fun deleteAllReminders() {
        remindersData.clear()
    }
}