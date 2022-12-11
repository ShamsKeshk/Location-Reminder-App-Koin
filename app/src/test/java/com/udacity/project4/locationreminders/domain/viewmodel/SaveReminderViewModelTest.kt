package com.udacity.project4.locationreminders.domain.viewmodel

import androidx.test.ext.junit.runners.AndroidJUnit4
import com.udacity.project4.R
import com.udacity.project4.locationreminders.domain.usecases.saveReminder.FakeSaveReminderUseCaseTest
import com.udacity.project4.locationreminders.framework.model.ReminderDataItem
import com.udacity.project4.testingUtils.getOrAwaitValue
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import org.junit.jupiter.api.Assertions.*
import org.junit.runner.RunWith
import org.koin.core.context.stopKoin

@RunWith(AndroidJUnit4::class)
internal class SaveReminderViewModelTest{

    private val reminderItem1 = ReminderDataItem("Google",
        "visit Silicon Valley","new York",
        5.122,-39.19225,"60")


    private lateinit var fakeSaceReminderUseCaseTest: FakeSaveReminderUseCaseTest
    private lateinit var saveReminderViewModel: SaveReminderViewModel

    @Before
    fun initializeModels(){
        stopKoin()
        fakeSaceReminderUseCaseTest = FakeSaveReminderUseCaseTest()
        saveReminderViewModel = SaveReminderViewModel(fakeSaceReminderUseCaseTest)
    }

    @Test
    fun setTitle_validTitle_triggeredEventWithSameValue(){
        //Given
        val title = "Udacity Team"

        //When
        saveReminderViewModel.setTitle(title)

        //Then
        assertEquals(saveReminderViewModel.reminderTitle.getOrAwaitValue(),title)
    }

    @Test
    fun setDescription_validDescription_triggeredEventWithSameValue(){
        //Given
        val description = "Udacity Have Great Stuff"

        //When
        saveReminderViewModel.setDescription(description)

        //Then
        assertEquals(saveReminderViewModel.reminderDescription.getOrAwaitValue(),description)
    }

    @Test
    fun validateEnteredData_validData_returnTrue(){
        //Give
        val reminderData = reminderItem1

        //When
        val validationResult = saveReminderViewModel.validateEnteredData(reminderData)

        //Then
        assertEquals(validationResult,true)
    }

    @Test
    fun validateEnteredData_invalidTitle_returnFalse(){
        //Give
        val reminderData = reminderItem1.copy()
        reminderData.title = null

        //When
        val validationResult = saveReminderViewModel.validateEnteredData(reminderData)

        //Then
        assertEquals(validationResult,false)
        assertEquals(saveReminderViewModel.showSnackBarInt.getOrAwaitValue(), R.string.err_enter_title)
    }

    @Test
    fun validateEnteredData_invalidLocation_returnFalse(){
        //Give
        val reminderData = reminderItem1.copy()
        reminderData.location = null

        //When
        val validationResult = saveReminderViewModel.validateEnteredData(reminderData)

        //Then
        assertEquals(validationResult,false)
        assertEquals(saveReminderViewModel.showSnackBarInt.getOrAwaitValue(), R.string.err_select_location)
    }

    @Test
    fun saveReminder_saveReminder_ReminderToastValueTriggered() = runTest{
        //When
        saveReminderViewModel.saveReminder(reminderItem1)

        //Then
        assertEquals(saveReminderViewModel.showToast.getOrAwaitValue(),R.string.reminder_saved)
    }
}