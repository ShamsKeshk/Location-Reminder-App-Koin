package com.udacity.project4.locationreminders.domain.viewmodel

import androidx.test.ext.junit.runners.AndroidJUnit4
import com.udacity.project4.locationreminders.domain.usecases.loadReminders.FakeLoadRemindersUseCaseTest
import com.udacity.project4.locationreminders.framework.model.ReminderDataItem
import com.udacity.project4.testingUtils.getOrAwaitValue
import org.junit.Before
import org.junit.Test
import org.junit.jupiter.api.Assertions.*
import org.junit.runner.RunWith
import org.koin.core.context.stopKoin

@RunWith(AndroidJUnit4::class)
internal class RemindersListViewModelTest{

    private lateinit var fakeLoadRemindersUseCaseImpl: FakeLoadRemindersUseCaseTest
    private lateinit var remindersListViewModel: RemindersListViewModel

    @Before
    fun initializeModels(){
        stopKoin()
        fakeLoadRemindersUseCaseImpl = FakeLoadRemindersUseCaseTest()

        remindersListViewModel = RemindersListViewModel(fakeLoadRemindersUseCaseImpl)
    }

    @Test
    fun loadReminders_fireEvent_eventTriggeredWithTrue(){

        remindersListViewModel.loadReminders()

        assertEquals(remindersListViewModel.remindersList.getOrAwaitValue(), emptyList<ReminderDataItem>())
    }

    @Test
    fun loadReminders_testLoadingEvent_eventTriggeredWithTrue(){

        //When
        remindersListViewModel.loadReminders()

        //Then
        assertEquals(remindersListViewModel.showLoading.getOrAwaitValue(), true)
    }

    @Test
    fun invalidateShowNoData_showNoDataEvent_eventTriggeredWithTrue(){
        //When
        remindersListViewModel.showNoData.value = false

        assertEquals(remindersListViewModel.showNoData.getOrAwaitValue(), false)
    }
}