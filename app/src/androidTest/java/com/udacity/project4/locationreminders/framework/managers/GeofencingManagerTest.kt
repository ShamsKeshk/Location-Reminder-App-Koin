package com.udacity.project4.locationreminders.framework.managers

import com.udacity.project4.locationreminders.framework.model.ReminderDataItem

internal class GeofencingManagerTest: GeofencingManager{

    private val geoFences = mutableListOf<ReminderDataItem>()

    override fun startAddSelectedGeofence(reminderDataItem: ReminderDataItem){
        geoFences.add(reminderDataItem)
    }
}