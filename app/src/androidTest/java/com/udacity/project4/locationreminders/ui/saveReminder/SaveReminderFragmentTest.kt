package com.udacity.project4.locationreminders.ui.saveReminder

import android.os.Bundle
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.fragment.app.FragmentFactory
import androidx.fragment.app.testing.FragmentScenario
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import com.udacity.project4.R
import com.udacity.project4.base.NavigationCommand
import com.udacity.project4.locationreminders.domain.viewmodel.SaveReminderViewModel
import com.udacity.project4.locationreminders.framework.managers.GeofencingManagerTest
import com.udacity.project4.locationreminders.framework.model.ReminderDataItem
import com.udacity.project4.util.getOrAwaitValue
import com.udacity.project4.util.launchFragmentInHiltContainer
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import dagger.hilt.android.testing.HiltTestApplication
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito
import org.robolectric.annotation.Config


@HiltAndroidTest
@Config(application = HiltTestApplication::class)
@RunWith(AndroidJUnit4::class)
@LargeTest
internal class SaveReminderFragmentTest{

    private val reminderItem = ReminderDataItem("Google",
        "we are on Silicon Valley, great Challenges Being there","new York",
        2.333,-33.45555,"45")

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    private lateinit var saveReminderViewModel: SaveReminderViewModel

    private lateinit var navController: NavController

    @Before
    fun initData() {
        hiltRule.inject()

        initFragmentScenario()
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

        launchFragmentInHiltContainer<SaveReminderFragment>(
            Bundle(), R.style.AppTheme,
            FragmentFactory()
        ) {
            Navigation.setViewNavController(this.view!!, navController)
            saveReminderViewModel = _viewModel
        }
    }
}