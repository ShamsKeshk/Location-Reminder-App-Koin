package com.udacity.project4.locationreminders.data

import com.udacity.project4.locationreminders.framework.repo.ReminderRepository
import com.udacity.project4.locationreminders.framework.local.data.ReminderDataEntity
import com.udacity.project4.locationreminders.framework.model.ReminderDataItem
import com.udacity.project4.locationreminders.framework.model.Result

//Use FakeDataSource that acts as a test double to the LocalDataSource
class FakeDataSource : ReminderRepository {

//    TODO: Create a fake data source to act as a double to the real data source

    override suspend fun getReminders(): List<ReminderDataItem> {
        TODO("Return the reminders")
    }

    override suspend fun saveReminder(reminder: ReminderDataEntity) {
        TODO("save the reminder")
    }

    override suspend fun getReminder(id: String): Result<ReminderDataEntity> {
        TODO("return the reminder with the id")
    }

    override suspend fun deleteAllReminders() {
        TODO("delete all the reminders")
    }


}