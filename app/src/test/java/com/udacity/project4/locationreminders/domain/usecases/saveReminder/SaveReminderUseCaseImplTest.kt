package com.udacity.project4.locationreminders.domain.usecases.saveReminder

import com.udacity.project4.locationreminders.framework.model.ReminderDataItem
import com.udacity.project4.locationreminders.framework.repo.FakeRemindersRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import org.junit.jupiter.api.Assertions.*

internal class SaveReminderUseCaseImplTest{


    private val reminderItem1 = ReminderDataItem("Google",
        "visit Silicon Valley","new York",
        5.122,-39.19225,"60")

    private lateinit var saveReminderUseCaseImpl: SaveReminderUseCaseImpl
    private lateinit var remindersRepository: FakeRemindersRepository

    @Before
    fun createRepositoryModel(){
        remindersRepository = FakeRemindersRepository()

        saveReminderUseCaseImpl = SaveReminderUseCaseImpl(remindersRepository)

    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun getReminders_saveReminder_returnSizeOne() = runTest {
        //Given
        saveReminderUseCaseImpl.saveReminder(reminderItem1)

        //When
        val addedItem = remindersRepository.getReminder("60").getCurrentData()

        //Then
        assertEquals(reminderItem1.id,addedItem?.id)
    }

}