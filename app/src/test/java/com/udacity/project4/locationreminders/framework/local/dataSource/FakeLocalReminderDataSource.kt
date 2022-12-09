package com.udacity.project4.locationreminders.framework.local.dataSource

import com.udacity.project4.locationreminders.framework.local.data.ReminderDataEntity

internal class FakeLocalReminderDataSource: LocalReminderDataSource{

    private val remindersData = mutableListOf<ReminderDataEntity>()

    override suspend fun getReminders(): List<ReminderDataEntity> {
        return remindersData
    }

    override suspend fun getReminderById(reminderId: String): ReminderDataEntity? {
        val item = remindersData.find {
            it.id == reminderId
        }

        return item
    }

    override suspend fun saveReminder(reminder: ReminderDataEntity) {
        remindersData.add(reminder)
    }

    override suspend fun deleteAllReminders() {
       remindersData.clear()
    }


}