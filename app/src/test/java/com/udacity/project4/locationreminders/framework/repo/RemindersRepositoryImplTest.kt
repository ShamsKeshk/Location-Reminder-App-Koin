package com.udacity.project4.locationreminders.framework.repo

import androidx.test.filters.SmallTest
import com.udacity.project4.locationreminders.domain.usecases.loadReminders.LoadRemindersUseCaseImpl
import com.udacity.project4.locationreminders.framework.local.dataSource.FakeLocalReminderDataSource
import com.udacity.project4.locationreminders.framework.local.dataSource.LocalReminderDataSource
import com.udacity.project4.locationreminders.framework.model.ReminderDataItem
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import org.junit.jupiter.api.Assertions.*

import kotlinx.coroutines.Dispatchers

@SmallTest
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
        remindersRepositoryImpl = RemindersRepositoryImpl(fakeLocalReminderDataSource, Dispatchers.Main)
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
        val result = loadRemindersUseCaseImpl.getReminders()


        val addedItem = result.getCurrentData()?.size

        //Then
        assertEquals(true,result.isSuccessful())
        assertEquals(1,addedItem)
    }



    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun clearReminders_removeData_returnZeroOrNull() = runTest {

        //When
        remindersRepository.deleteAllReminders()

        //Then
        val result = loadRemindersUseCaseImpl.getReminders()
        val addedItem = result.getCurrentData()?.size

        assertEquals(true,result.isSuccessful())
        assertEquals(0,addedItem)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun getRemindersById_removeData_returnZeroOrNull() = runTest {

        //Given
        remindersRepository.saveReminder(reminderItem2)

        //When
        val result = remindersRepository.getReminder("90")
        val addedItem = result.getCurrentData()

        //Then
        assertEquals(true,result.isSuccessful())
        assertEquals(reminderItem2.id,addedItem?.id)
    }

}