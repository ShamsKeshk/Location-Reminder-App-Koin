package com.udacity.project4.locationreminders.framework.managers

import com.udacity.project4.locationreminders.framework.model.ReminderDataItem

interface GeofencingManager {

    fun startAddSelectedGeofence(reminderDataItem: ReminderDataItem)

}