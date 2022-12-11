package com.udacity.project4.locationreminders.framework.local.database

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.SmallTest
import com.udacity.project4.di.getKoinTestingModules
import com.udacity.project4.locationreminders.framework.model.ReminderDataItem
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.*
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
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
internal class RemindersDatabaseTest: KoinTest {

    private val reminderItem1 = ReminderDataItem("Google",
        "visit Silicon Valley","new York",
        5.122,-39.19225)

    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()


    val database by inject<RemindersDatabase>(named("testing"))

    @Before
    fun initializeDatabase() {
        stopKoin()
        startKoin {
            androidLogger()
            androidContext(ApplicationProvider.getApplicationContext())
            modules(getKoinTestingModules())
        }
    }

    @Test
    fun saveReminder_andRetrieveItAgain() = runTest{
        database.reminderDao().saveReminder(reminderItem1.asEntity())

        val reminderEntity = database.reminderDao().getReminderById(reminderItem1.id)
        assertEquals(reminderItem1.title,reminderEntity?.title)
    }

    @Test
    fun saveReminder_andDeleteItAgain() = runTest{
        database.reminderDao().saveReminder(reminderItem1.asEntity())

        val reminderEntity = database.reminderDao().getReminderById(reminderItem1.id)
        assertEquals(reminderItem1.title,reminderEntity?.title)

        database.reminderDao().deleteAllReminders()

        val reminders = database.reminderDao().getReminders().size

        assertEquals(reminders,0)

    }

    @Test
    fun getReminder_withInvalidId_shouldReturnNull() = runTest{

        val reminderEntity = database.reminderDao().getReminderById(reminderItem1.id)
        assertEquals(null,reminderEntity?.title)
    }

    @After
    fun closeDatabaseConnection(){
        database.close()
    }

}