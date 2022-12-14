package com.udacity.project4.locationreminders.framework.repo

import androidx.test.filters.SmallTest
import com.udacity.project4.locationreminders.framework.local.dataSource.FakeLocalReminderDataSource
import com.udacity.project4.locationreminders.framework.local.dataSource.LocalReminderDataSource
import com.udacity.project4.locationreminders.framework.model.ReminderDataItem
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import org.junit.jupiter.api.Assertions.*

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
        remindersRepositoryImpl = RemindersRepositoryImpl(fakeLocalReminderDataSource)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun getReminders_addItem_returnSizeOne() = runTest {
        //Given
        remindersRepositoryImpl.saveReminder(reminderItem1)

        //When
        val result = remindersRepositoryImpl.getReminders()


        val addedItem = result.getCurrentData()?.size

        //Then
        assertEquals(true,result.isSuccessful())
        assertEquals(1,addedItem)
    }



    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun clearReminders_removeData_returnZeroOrNull() = runTest {

        //When
        remindersRepositoryImpl.deleteAllReminders()

        //Then
        val result = remindersRepositoryImpl.getReminders()
        val addedItem = result.getCurrentData()?.size

        assertEquals(true,result.isSuccessful())
        assertEquals(0,addedItem)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun getRemindersById_removeData_returnZeroOrNull() = runTest {

        //Given
        remindersRepositoryImpl.saveReminder(reminderItem2)

        //When
        val result = remindersRepositoryImpl.getReminder("90")
        val addedItem = result.getCurrentData()

        //Then
        assertEquals(true,result.isSuccessful())
        assertEquals(reminderItem2.id,addedItem?.id)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun getRemindersById_passInvalidId_returnErrorResult() = runTest {

        //Given
        remindersRepositoryImpl.saveReminder(reminderItem2)

        //When
        val result = remindersRepositoryImpl.getReminder("invalidId")
        val error = result.getCurrentError()

        //Then
        assertEquals(true,result.isFailed())
        assertEquals("Reminder not found!",error?.message)
    }

}