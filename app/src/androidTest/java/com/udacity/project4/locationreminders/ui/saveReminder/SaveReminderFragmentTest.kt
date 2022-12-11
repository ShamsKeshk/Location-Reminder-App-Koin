package com.udacity.project4.locationreminders.ui.saveReminder

import android.app.Application
import android.os.Bundle
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import com.udacity.project4.R
import com.udacity.project4.base.NavigationCommand
import com.udacity.project4.di.getKoinTestingModules
import com.udacity.project4.locationreminders.domain.viewmodel.SaveReminderViewModel
import com.udacity.project4.locationreminders.framework.model.ReminderDataItem
import com.udacity.project4.util.getOrAwaitValue
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import org.mockito.Mockito

import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.stopKoin
import org.koin.test.KoinTest

@RunWith(AndroidJUnit4::class)
@LargeTest
internal class SaveReminderFragmentTest: KoinTest{

    private val reminderItem = ReminderDataItem("Google",
        "we are on Silicon Valley, great Challenges Being there","new York",
        2.333,-33.45555,"45")

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var appContext: Application

    private lateinit var saveReminderViewModel: SaveReminderViewModel

    private lateinit var navController: NavController


    @Before
    fun initData() {
        initKoinModules()
        initFragmentScenario()
    }

    fun initKoinModules(){
        stopKoin()
        startKoin {
            androidLogger()
            androidContext(ApplicationProvider.getApplicationContext())
            modules(getKoinTestingModules())
        }
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun fillReminderDataWithoutTitle_snackBarMessageShouldBeTitleMissing() = runTest{
        saveReminderViewModel.setSelectedLocation(reminderItem.location!!)
        saveReminderViewModel.latitude.value = reminderItem.latitude
        saveReminderViewModel.longitude.value = reminderItem.longitude

        onView(withId(R.id.reminderDescription)).perform(typeText(reminderItem.description),closeSoftKeyboard())
        onView(withId(R.id.saveReminder)).perform(ViewActions.click())

        val data = saveReminderViewModel.showSnackBarInt.getOrAwaitValue()

        assertEquals(data,R.string.err_enter_title)

    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun fillReminderDataWithoutLocation_snackBarMessageShouldBeLocationMissing() = runTest{
        saveReminderViewModel.latitude.value = reminderItem.latitude
        saveReminderViewModel.longitude.value = reminderItem.longitude

        onView(withId(R.id.reminderTitle)).perform(typeText(reminderItem.title),closeSoftKeyboard())
        onView(withId(R.id.reminderDescription)).perform(typeText(reminderItem.description),closeSoftKeyboard())

        onView(withId(R.id.saveReminder)).perform(ViewActions.click())

        val data = saveReminderViewModel.showSnackBarInt.getOrAwaitValue()

        assertEquals(data,R.string.err_select_location)

    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun fillReminderDataCompletely_dataShouldBeSavedAndNavigatedToListScreen() = runTest{
        saveReminderViewModel.latitude.value = reminderItem.latitude
        saveReminderViewModel.longitude.value = reminderItem.longitude
        saveReminderViewModel.setSelectedLocation(reminderItem.location!!)

        onView(withId(R.id.reminderTitle)).perform(typeText(reminderItem.title),closeSoftKeyboard())
        onView(withId(R.id.reminderDescription)).perform(typeText(reminderItem.description),closeSoftKeyboard())

        onView(withId(R.id.saveReminder)).perform(ViewActions.click())

        // THEN - Verify that we navigate to the add screen
        assertEquals(NavigationCommand.Back,saveReminderViewModel.navigationCommand.getOrAwaitValue())

        Mockito.verify(navController).popBackStack()
    }

    private fun initFragmentScenario(){
        navController = Mockito.mock(NavController::class.java)

        val scenario = launchFragmentInContainer<SaveReminderFragment>(Bundle(), R.style.AppTheme)

        scenario.onFragment {
            Navigation.setViewNavController(it.view!!, navController)
            saveReminderViewModel = it._viewModel
        }
    }
}