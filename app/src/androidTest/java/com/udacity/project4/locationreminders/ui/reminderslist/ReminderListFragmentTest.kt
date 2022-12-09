package com.udacity.project4.locationreminders.ui.reminderslist

import android.os.Bundle
import androidx.fragment.app.FragmentFactory
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.Espresso
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import androidx.test.filters.MediumTest
import org.junit.runner.RunWith
import com.udacity.project4.R
import com.udacity.project4.locationreminders.domain.usecases.saveReminder.SaveReminderUseCaseImpl
import com.udacity.project4.locationreminders.framework.model.ReminderDataItem
import com.udacity.project4.util.launchFragmentInHiltContainer
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import dagger.hilt.android.testing.HiltTestApplication
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito
import org.robolectric.annotation.Config
import javax.inject.Inject

@HiltAndroidTest
@Config(application = HiltTestApplication::class)
@RunWith(AndroidJUnit4::class)
@LargeTest
internal class ReminderListFragmentTest{

    private val reminderItem1 = ReminderDataItem("udacity",
        "visit udacity location","new York",
        2.333,-33.45555,"45")

    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @Inject
    lateinit var saveReminderUseCaseImpl: SaveReminderUseCaseImpl

    @Before
    fun initData() {
        hiltRule.inject()
    }


    @Test
    fun clickAddReminder() = runTest{
        saveReminderUseCaseImpl.saveReminder(reminderItem1)
        val navController = Mockito.mock(NavController::class.java)
        val scenario = launchFragmentInHiltContainer<ReminderListFragment>(Bundle(), R.style.AppTheme,
            FragmentFactory()
        ){
            Navigation.setViewNavController(this.view!!, navController)
        }


        // WHEN - Click on the "+" button
        Espresso.onView(withId(R.id.addReminderFAB)).perform(ViewActions.click())

        // THEN - Verify that we navigate to the add screen
        Mockito.verify(navController).navigate(
            ReminderListFragmentDirections.toSaveReminder()
        )
    }

    @Test
    fun saveItemAndDetectWhetherItsDisplayedOrNot() = runTest{
        //Given
        saveReminderUseCaseImpl.saveReminder(reminderItem1)
        val navController = Mockito.mock(NavController::class.java)
        val scenario = launchFragmentInHiltContainer<ReminderListFragment>(Bundle(), R.style.AppTheme,
            FragmentFactory()
        ){
            Navigation.setViewNavController(this.view!!, navController)
        }

        // WHEN - Click on the first list item
        Espresso.onView(withId(R.id.reminderssRecyclerView))
            .perform(
                RecyclerViewActions.actionOnItem<RecyclerView.ViewHolder>(
                    //Then Verify whether we have a view with the added item
                    ViewMatchers.hasDescendant(ViewMatchers.withText(reminderItem1.title)), ViewActions.click()
                ))
    }




}