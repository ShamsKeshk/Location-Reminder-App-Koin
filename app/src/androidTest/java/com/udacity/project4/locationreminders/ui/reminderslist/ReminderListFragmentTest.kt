package com.udacity.project4.locationreminders.ui.reminderslist

import android.os.Bundle
import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.Espresso
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import org.junit.runner.RunWith
import com.udacity.project4.R
import com.udacity.project4.di.getKoinTestingModules
import com.udacity.project4.locationreminders.domain.usecases.saveReminder.SaveReminderUseCase
import com.udacity.project4.locationreminders.framework.model.ReminderDataItem
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.test.KoinTest
import org.koin.test.inject
import org.mockito.Mockito

@RunWith(AndroidJUnit4::class)
@LargeTest
internal class ReminderListFragmentTest : KoinTest {

    private val reminderItem1 = ReminderDataItem("udacity",
        "visit udacity location","new York",
        2.333,-33.45555,"45")

    val saveReminderUseCaseImpl: SaveReminderUseCase by inject<SaveReminderUseCase>()

    @Before
    fun initTest(){

        stopKoin()
        startKoin {
            androidLogger()
            androidContext(ApplicationProvider.getApplicationContext())
            modules(getKoinTestingModules())
        }
    }


    @Test
    fun clickAddReminder() = runTest{
        saveReminderUseCaseImpl.saveReminder(reminderItem1)
        val navController = Mockito.mock(NavController::class.java)

        val scenario = launchFragmentInContainer<ReminderListFragment>(Bundle(), R.style.AppTheme)

        scenario.onFragment {
            navController.setGraph(R.navigation.nav_graph)
            Navigation.setViewNavController(it.requireView(), navController)
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

        val scenario = launchFragmentInContainer<ReminderListFragment>(Bundle(), R.style.AppTheme)

        scenario.onFragment {
            navController.setGraph(R.navigation.nav_graph)

            Navigation.setViewNavController(it.requireView(), navController)
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