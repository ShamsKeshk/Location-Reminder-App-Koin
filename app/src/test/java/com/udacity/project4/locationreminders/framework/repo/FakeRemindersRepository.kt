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

        if (shouldReturnError)
            return Result.Error(Throwable(fakeGetReminderErrorMessage))

       val item = remindersData.find {
           it.id == id
       }

        return if(item != null){
            Result.Success(item)
        }else {
            Result.Error(Throwable("Reminder not found!"))
        }
    }

    override suspend fun deleteAllReminders() {
        remindersData.clear()
    }
}