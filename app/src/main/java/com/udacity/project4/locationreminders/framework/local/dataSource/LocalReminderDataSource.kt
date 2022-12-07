package com.udacity.project4.locationreminders.framework.local.dataSource

import com.udacity.project4.locationreminders.framework.local.data.ReminderDataEntity

interface LocalReminderDataSource {

    /**
     * @return all reminders.
     */
    suspend fun getReminders(): List<ReminderDataEntity>

    /**
     * @param reminderId the id of the reminder
     * @return the reminder object with the reminderId
     */
    suspend fun getReminderById(reminderId: String): ReminderDataEntity?

    /**
     * Insert a reminder in the database. If the reminder already exists, replace it.
     *
     * @param reminder the reminder to be inserted.
     */
    suspend fun saveReminder(reminder: ReminderDataEntity)

    /**
     * Delete all reminders.
     */
    suspend fun deleteAllReminders()
}