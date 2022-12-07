package com.udacity.project4.locationreminders.framework.repo

import com.udacity.project4.locationreminders.framework.local.data.ReminderDataEntity
import com.udacity.project4.locationreminders.framework.local.dataSource.LocalReminderDataSource
import com.udacity.project4.locationreminders.framework.local.dataSource.LocalReminderDataSourceImpl
import com.udacity.project4.locationreminders.framework.model.Result
import com.udacity.project4.locationreminders.framework.model.ReminderDataItem
import kotlinx.coroutines.*
import javax.inject.Inject

/**
 * Concrete implementation of a data source as a db.
 *
 * The repository is implemented so that you can focus on only testing it.
 *
 * @param remindersDao the dao that does the Room db operations
 * @param ioDispatcher a coroutine dispatcher to offload the blocking IO tasks
 */
class RemindersRepositoryImpl @Inject constructor(private val localDataSourceImpl: LocalReminderDataSource,
                                                  private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
) : ReminderRepository {

    /**
     * Get the reminders list from the local db
     * @return Result the holds a Success with all the reminders or an Error object with the error message
     */
    override suspend fun getReminders(): List<ReminderDataItem> {
        return localDataSourceImpl.getReminders().map {
            it.asDomain()
        }
    }

    /**
     * Insert a reminder in the db.
     * @param reminder the reminder to be inserted
     */
    override suspend fun saveReminder(reminder: ReminderDataEntity) =
        withContext(ioDispatcher) {
            localDataSourceImpl.saveReminder(reminder)
        }

    /**
     * Get a reminder by its id
     * @param id to be used to get the reminder
     * @return Result the holds a Success object with the Reminder or an Error object with the error message
     */
    override suspend fun getReminder(id: String): Result<ReminderDataEntity> = withContext(ioDispatcher) {
        try {
            val reminder = localDataSourceImpl.getReminderById(id)
            if (reminder != null) {
                return@withContext Result.Success(reminder)
            } else {
                return@withContext Result.Error(Throwable("Reminder not found!"))
            }
        } catch (e: Exception) {
            return@withContext Result.Error(e)
        }
    }

    /**
     * Deletes all the reminders in the db
     */
    override suspend fun deleteAllReminders() {
        withContext(ioDispatcher) {
            localDataSourceImpl.deleteAllReminders()
        }
    }
}