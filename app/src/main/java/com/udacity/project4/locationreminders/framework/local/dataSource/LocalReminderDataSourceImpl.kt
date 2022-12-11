package com.udacity.project4.locationreminders.framework.local.dataSource

import com.udacity.project4.locationreminders.framework.local.data.ReminderDataEntity
import com.udacity.project4.locationreminders.framework.local.database.RemindersDatabase

class LocalReminderDataSourceImpl constructor(val remindersDatabase: RemindersDatabase) : LocalReminderDataSource {

    override suspend fun getReminders(): List<ReminderDataEntity> {
        return remindersDatabase.reminderDao().getReminders()
    }

    override suspend fun getReminderById(reminderId: String): ReminderDataEntity? {
        return remindersDatabase.reminderDao().getReminderById(reminderId)
    }

    override suspend fun saveReminder(reminder: ReminderDataEntity) {
        return remindersDatabase.reminderDao().saveReminder(reminder)
    }

    override suspend fun deleteAllReminders() {
        remindersDatabase.reminderDao().deleteAllReminders()
    }
}