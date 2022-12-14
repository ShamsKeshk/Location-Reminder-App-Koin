package com.udacity.project4.locationreminders.framework.repo

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.SmallTest
import com.udacity.project4.di.getKoinTestingModules
import com.udacity.project4.locationreminders.framework.local.dataSource.LocalReminderDataSource
import com.udacity.project4.locationreminders.framework.local.dataSource.LocalReminderDataSourceImpl
import com.udacity.project4.locationreminders.framework.local.database.RemindersDatabase
import com.udacity.project4.locationreminders.framework.model.ReminderDataItem
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import org.junit.jupiter.api.Assertions.*

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.test.StandardTestDispatcher
import org.junit.Rule
import org.junit.runner.RunWith
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.core.qualifier.named
import org.koin.test.KoinTest
import org.koin.test.inject

@ExperimentalCoroutinesApi
@RunWith(AndroidJUnit4::class)
@SmallTest
internal class RemindersRepositoryImplTest: KoinTest{

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

    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    val database by inject<RemindersDatabase>(named("testing"))

    @Before
    fun createDataSourceAndRepo(){
        initializeDatabase()
        remindersRepositoryImpl = RemindersRepositoryImpl(LocalReminderDataSourceImpl(database),Dispatchers.Main)
    }


    fun initializeDatabase() {
        stopKoin()
        startKoin {
            androidLogger()
            androidContext(ApplicationProvider.getApplicationContext())
            modules(getKoinTestingModules())
        }
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
    fun getRemindersById_passInvalidId_returnErrorResult() = runTest(StandardTestDispatcher()) {

        //Given
        remindersRepositoryImpl.saveReminder(reminderItem2)

        //When
        val result = remindersRepositoryImpl.getReminder("inValidId")
        val error = result.getCurrentError()

        //Then
        assertEquals(true,result.isFailed())
        assertEquals("Reminder not found!",error?.message)
    }

}