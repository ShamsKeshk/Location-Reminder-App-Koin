package com.udacity.project4.locationreminders.domain.usecases.loadReminders

import com.udacity.project4.locationreminders.framework.model.ReminderDataItem
import com.udacity.project4.locationreminders.framework.repo.FakeRemindersRepository
//import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import org.junit.jupiter.api.Assertions.*


internal class LoadRemindersUseCaseImplTest{

    private val reminderItem1 = ReminderDataItem("udacity",
        "visit udacity location","new York",
        2.333,-33.45555,"45")

    private val reminderItem2 = ReminderDataItem("udacity",
        "visit udacity location","new York",
        2.333,-33.45555)

    private val reminderItem3 = ReminderDataItem("udacity",
        "visit udacity location","new York",
        2.333,-33.45555)

    private lateinit var loadRemindersUseCaseImpl: LoadRemindersUseCaseImpl
    private lateinit var remindersRepository: FakeRemindersRepository

    @Before
    fun createRepositoryModel(){
        remindersRepository = FakeRemindersRepository()

        loadRemindersUseCaseImpl = LoadRemindersUseCaseImpl(remindersRepository)

    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun getReminders_addThreeItems_returnSizeThree() = runTest {
        //Given
        remindersRepository.saveReminder(reminderItem1)
        remindersRepository.saveReminder(reminderItem2)
        remindersRepository.saveReminder(reminderItem3)

        //When
        val addedItem = loadRemindersUseCaseImpl.getReminders().size

        //Then
        assertEquals(3,addedItem)
    }



    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun getReminders_removeData_returnZeroOrNull() = runTest {

        //When
        remindersRepository.deleteAllReminders()

        //Then
        val addedItem = loadRemindersUseCaseImpl.getReminders().size
        assertEquals(0,addedItem)
    }
}

