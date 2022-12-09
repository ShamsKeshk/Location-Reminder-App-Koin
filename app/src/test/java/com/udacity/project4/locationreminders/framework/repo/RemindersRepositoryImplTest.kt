package com.udacity.project4.locationreminders.framework.repo

import com.udacity.project4.locationreminders.domain.usecases.loadReminders.LoadRemindersUseCaseImpl
import com.udacity.project4.locationreminders.framework.local.dataSource.FakeLocalReminderDataSource
import com.udacity.project4.locationreminders.framework.local.dataSource.LocalReminderDataSource
import com.udacity.project4.locationreminders.framework.model.ReminderDataItem
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import org.junit.jupiter.api.Assertions.*

internal class RemindersRepositoryImplTest{

    private val reminderItem1 = ReminderDataItem("udacity",
        "visit udacity location","new York",
        2.333,-33.45555,"45")

    private val reminderItem2 = ReminderDataItem("udacity",
        "visit udacity location","new York",
        2.333,-33.45555,"90")

    private val reminderItem3 = ReminderDataItem("udacity",
        "visit udacity location","new York",
        2.333,-33.45555)


    private lateinit var fakeLocalReminderDataSource: LocalReminderDataSource
    private lateinit var remindersRepositoryImpl: RemindersRepositoryImpl

    @Before
    fun createDataSourceAndRepo(){
        fakeLocalReminderDataSource = FakeLocalReminderDataSource()
        remindersRepositoryImpl = RemindersRepositoryImpl(fakeLocalReminderDataSource)
    }



    private lateinit var loadRemindersUseCaseImpl: LoadRemindersUseCaseImpl
    private lateinit var remindersRepository: FakeRemindersRepository

    @Before
    fun createRepositoryModel(){
        remindersRepository = FakeRemindersRepository()

        loadRemindersUseCaseImpl = LoadRemindersUseCaseImpl(remindersRepository)

    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun getReminders_addItem_returnSizeOne() = runTest {
        //Given
        remindersRepository.saveReminder(reminderItem1)

        //When
        val addedItem = loadRemindersUseCaseImpl.getReminders().size

        //Then
        assertEquals(1,addedItem)
    }



    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun clearReminders_removeData_returnZeroOrNull() = runTest {

        //When
        remindersRepository.deleteAllReminders()

        //Then
        val addedItem = loadRemindersUseCaseImpl.getReminders().size
        assertEquals(0,addedItem)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun getRemindersById_removeData_returnZeroOrNull() = runTest {

        //Given
        remindersRepository.saveReminder(reminderItem2)

        //When
        val addedItem = remindersRepository.getReminder("90").getCurrentData()

        //Then
        assertEquals(reminderItem2.id,addedItem?.id)
    }

}