package com.udacity.project4

import android.app.Application
import androidx.test.core.app.ApplicationProvider.getApplicationContext
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import com.udacity.project4.locationreminders.framework.repo.ReminderRepository
import com.udacity.project4.locationreminders.framework.local.database.RemindersDao
import com.udacity.project4.locationreminders.domain.viewmodel.SaveReminderViewModel
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Rule
import org.junit.runner.RunWith
import javax.inject.Inject

@RunWith(AndroidJUnit4::class)
@LargeTest
//END TO END test to black box test the app
@HiltAndroidTest
class RemindersActivityTest {

    private lateinit var appContext: Application


    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @Inject
    lateinit var repository: ReminderRepository


    @Inject
    lateinit var remindersDao: RemindersDao

    lateinit var saveReminderViewModel: SaveReminderViewModel

    @Before
    fun init() {
        // Populate @Inject fields in test class
        hiltRule.inject()

        saveReminderViewModel = SaveReminderViewModel(repository)

        appContext = getApplicationContext()

        //clear the data to start fresh
        runBlocking {
            repository.deleteAllReminders()
        }
    }


//    TODO: add End to End testing to the app

}
