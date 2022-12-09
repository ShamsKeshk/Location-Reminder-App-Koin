package com.udacity.project4.locationreminders.domain.usecases.saveReminder

import com.udacity.project4.locationreminders.framework.model.ReminderDataItem
import com.udacity.project4.locationreminders.framework.repo.FakeRemindersRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import org.junit.jupiter.api.Assertions.*

internal class FakeSaveReminderUseCaseTest: SaveReminderUseCase{

    private val remindersData = mutableListOf<ReminderDataItem>()

    override suspend fun saveReminder(reminderDataItem: ReminderDataItem) {
        remindersData.add(reminderDataItem)
    }

}