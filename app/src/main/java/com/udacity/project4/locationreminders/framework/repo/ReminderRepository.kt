package com.udacity.project4.locationreminders.framework.repo

import com.udacity.project4.locationreminders.framework.local.data.ReminderDataEntity
import com.udacity.project4.locationreminders.framework.model.ReminderDataItem
import com.udacity.project4.locationreminders.framework.model.Result

/**
 * Main entry point for accessing reminders data.
 */
interface ReminderRepository {
    suspend fun getReminders(): Result<List<ReminderDataItem>>
    suspend fun saveReminder(reminder: ReminderDataItem)
    suspend fun getReminder(id: String): Result<ReminderDataItem?>
    suspend fun deleteAllReminders()
}