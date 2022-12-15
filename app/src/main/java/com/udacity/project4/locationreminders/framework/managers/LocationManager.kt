package com.udacity.project4.locationreminders.framework.managers

import android.content.IntentSender
import androidx.activity.result.ActivityResult
import androidx.activity.result.IntentSenderRequest
import androidx.activity.result.contract.ActivityResultContract
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import com.google.android.gms.common.api.ResolvableApiException
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.LocationSettingsRequest



class LocationManager(val activity: FragmentActivity,
                      val fragment: Fragment,
                      val deviceLocationSettingCallback: DeviceLocationSettingCallback) {

    private val registerDeviceIntentSender = fragment.registerForActivityResult(intentSenderForResult()) {
        checkDeviceLocationSettings(false)
    }


    fun checkDeviceLocationSettings(resolve:Boolean = true) {

        val builder = LocationSettingsRequest.Builder().addLocationRequest(createLocationRequest())
        val settingsClient = LocationServices.getSettingsClient(activity)
        val locationSettingsResponseTask = settingsClient.checkLocationSettings(builder.build())

        locationSettingsResponseTask.addOnFailureListener { exception ->
            if (exception is ResolvableApiException && resolve){
                try {
                    registerDeviceIntentSender.launch(createIntentSenderRequest(exception))
                } catch (sendEx: IntentSender.SendIntentException) {
                    deviceLocationSettingCallback.onDeviceLocationSettingFailed(exception)
                }
            } else {
                deviceLocationSettingCallback.onDeviceLocationSettingFailed()
            }
        }
        locationSettingsResponseTask.addOnCompleteListener {
            if ( it.isSuccessful ) {
                deviceLocationSettingCallback.onDeviceLocationSettingEnabled()
            }
        }
    }

    private fun createLocationRequest(): LocationRequest{
        return  LocationRequest.create().apply {
            priority = LocationRequest.PRIORITY_LOW_POWER
        }
    }

    interface DeviceLocationSettingCallback{
        fun onDeviceLocationSettingEnabled()
        fun onDeviceLocationSettingFailed(exception: Exception? = null)
    }

    private fun intentSenderForResult(): ActivityResultContract<IntentSenderRequest, ActivityResult>{
        return ActivityResultContracts.StartIntentSenderForResult()
    }

    private fun createIntentSenderRequest(resolvableApiException: ResolvableApiException): IntentSenderRequest{
        return IntentSenderRequest.Builder(resolvableApiException.resolution).build()
    }
}