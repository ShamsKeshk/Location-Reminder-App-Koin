package com.udacity.project4.locationreminders.framework.local.dataSource

import com.udacity.project4.locationreminders.framework.local.data.ReminderDataEntity
import com.udacity.project4.locationreminders.framework.local.database.RemindersDao
import javax.inject.Inject

class LocalReminderDataSourceImpl @Inject constructor(val remindersDao: RemindersDao) : LocalReminderDataSource {

    override suspend fun getReminders(): List<ReminderDataEntity> {
        return remindersDao.getReminders()
    }

    override suspend fun getReminderById(reminderId: String): ReminderDataEntity? {
        return remindersDao.getReminderById(reminderId)
    }

    override suspend fun saveReminder(reminder: ReminderDataEntity) {
        return remindersDao.saveReminder(reminder)
    }

    override suspend fun deleteAllReminders() {
       remindersDao.deleteAllReminders()
    }
}