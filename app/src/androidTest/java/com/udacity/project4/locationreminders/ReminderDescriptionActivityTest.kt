package com.udacity.project4.locationreminders

import android.content.Intent
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import androidx.test.filters.MediumTest
import androidx.test.rule.ActivityTestRule
import com.udacity.project4.locationreminders.framework.model.ReminderDataItem
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

import com.udacity.project4.R


@RunWith(AndroidJUnit4::class)
@MediumTest
internal class ReminderDescriptionActivityTest{

    private val reminderItem1 = ReminderDataItem("udacity",
        "visit udacity location","new York",
        2.333,-33.45555,"45")

    @get:Rule
    val rule = ActivityTestRule(ReminderDescriptionActivity::class.java,
        true,
        false) // launch activity later -> if its true, the activity will start here


    fun getIntent(): Intent{
        return Intent().apply {
            putExtra("EXTRA_ReminderDataItem",reminderItem1)
        }
    }

    @Test
    fun testItemBind_nameToBeDisplayed(){
        rule.launchActivity(getIntent())
        onView(withId(R.id.tv_reminder_title)).check(
            ViewAssertions.matches(
                ViewMatchers.withText(
                    reminderItem1.title
                )
            )
        )

        onView(withId(R.id.tv_reminder_description)).check(
            ViewAssertions.matches(
                ViewMatchers.withText(
                    reminderItem1.description
                )
            )
        )

        onView(withId(R.id.tv_reminder_location)).check(
            ViewAssertions.matches(
                ViewMatchers.withText(
                    reminderItem1.location
                )
            )
        )
    }


}