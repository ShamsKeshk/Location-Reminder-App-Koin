package com.udacity.project4.locationreminders.framework.repo

import com.udacity.project4.locationreminders.framework.local.data.ReminderDataEntity
import com.udacity.project4.locationreminders.framework.model.ReminderDataItem
import com.udacity.project4.locationreminders.framework.model.Result

/**
 * Main entry point for accessing reminders data.
 */
interface ReminderRepository {
    suspend fun getReminders(): List<ReminderDataItem>
    suspend fun saveReminder(reminder: ReminderDataEntity)
    suspend fun getReminder(id: String): Result<ReminderDataEntity>
    suspend fun deleteAllReminders()
}