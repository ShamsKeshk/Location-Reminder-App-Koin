package com.udacity.project4.locationreminders.framework.managers

import android.annotation.SuppressLint
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import com.google.android.gms.location.Geofence
import com.google.android.gms.location.GeofencingClient
import com.google.android.gms.location.GeofencingRequest
import com.google.android.gms.location.LocationServices
import com.udacity.project4.locationreminders.framework.model.ReminderDataItem
import com.udacity.project4.locationreminders.framework.receivers.GeofenceBroadcastReceiver
import dagger.hilt.android.qualifiers.ActivityContext
import javax.inject.Inject

private const val GEOFENCE_RADIUS_IN_METERS = 1609f
private const val GEOFENCE_EXPIRATION_IN_HOURS: Long = 12
private const val GEOFENCE_EXPIRATION_IN_MILLISECONDS: Long = GEOFENCE_EXPIRATION_IN_HOURS * 60 * 60 * 1000

class GeofencingManagerImpl @Inject constructor(@ActivityContext val context: Context): GeofencingManager {

    private var mGeofencingClient: GeofencingClient? = null

    init {
        initGeofencingClient()
    }

    private fun initGeofencingClient(){
        mGeofencingClient = LocationServices.getGeofencingClient(context)
    }

    private fun getGeofencingRequest(geofence: Geofence): GeofencingRequest {
        return GeofencingRequest.Builder()
            .setInitialTrigger(GeofencingRequest.INITIAL_TRIGGER_ENTER)
            .addGeofence(geofence)
            .build()
    }

    @SuppressLint("MissingPermission")
    override fun startAddSelectedGeofence(reminderDataItem: ReminderDataItem){
        val geofence = createGeofenceModel(reminderDataItem)
        mGeofencingClient!!.addGeofences(getGeofencingRequest(geofence), getGeofencePendingIntent())
    }

    private fun getGeofencePendingIntent(): PendingIntent {
        val intent = Intent(context, GeofenceBroadcastReceiver::class.java)
        return PendingIntent.getBroadcast(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE)
    }

    private fun createGeofenceModel(reminderDataItem: ReminderDataItem): Geofence{
        return Geofence.Builder()
            .setRequestId(reminderDataItem.id)
            .setCircularRegion(
                reminderDataItem.latitude!!,
                reminderDataItem.longitude!!,
                GEOFENCE_RADIUS_IN_METERS)
            .setExpirationDuration(GEOFENCE_EXPIRATION_IN_MILLISECONDS)
            .setTransitionTypes(
                Geofence.GEOFENCE_TRANSITION_ENTER or
                        Geofence.GEOFENCE_TRANSITION_EXIT)
            .build()
    }

}