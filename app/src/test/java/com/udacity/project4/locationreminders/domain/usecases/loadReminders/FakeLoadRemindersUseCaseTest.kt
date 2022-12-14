package com.udacity.project4.locationreminders.domain.usecases.loadReminders

import com.udacity.project4.locationreminders.framework.model.ReminderDataItem
import com.udacity.project4.locationreminders.framework.model.Result

class FakeLoadRemindersUseCaseTest: LoadRemindersUseCase {

    var shouldReturnError = false
    val defaultErrorMessage = "error getting Reminders"

    private val remindersData = mutableListOf<ReminderDataItem>()

    override suspend fun getReminders(): Result<List<ReminderDataItem>> {
        return if (shouldReturnError){
            Result.Error(Throwable(defaultErrorMessage))
        }else {
            Result.Success(remindersData)
        }
    }
}