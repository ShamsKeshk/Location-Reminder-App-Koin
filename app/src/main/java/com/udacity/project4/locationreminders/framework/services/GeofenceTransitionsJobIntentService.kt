/*
 * Copyright 2017 Google Inc. All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.udacity.project4.locationreminders.framework.services

import android.content.Context
import android.content.Intent
import androidx.core.app.JobIntentService
import com.google.android.gms.location.Geofence
import com.google.android.gms.location.GeofencingEvent
import com.udacity.project4.locationreminders.framework.repo.ReminderRepository
import com.udacity.project4.locationreminders.framework.local.data.ReminderDataEntity
import com.udacity.project4.locationreminders.framework.model.ReminderDataItem
import com.udacity.project4.utils.sendNotification
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import com.udacity.project4.locationreminders.framework.model.Result


class GeofenceTransitionsJobIntentService(private val reminderRepository: ReminderRepository? = null) : JobIntentService() {


    constructor(): this(null)

    override fun onHandleWork(intent: Intent) {
        val geofencingEvent = GeofencingEvent.fromIntent(intent)
        if (geofencingEvent!!.hasError()) {
            return
        }

        val geofenceTransition = geofencingEvent.geofenceTransition

        // Test that the reported transition was of interest.
        if (geofenceTransition == Geofence.GEOFENCE_TRANSITION_ENTER ||
            geofenceTransition == Geofence.GEOFENCE_TRANSITION_EXIT
        ) {

            val triggeringGeofences = geofencingEvent.triggeringGeofences

            if (triggeringGeofences.isNullOrEmpty())
                return



            CoroutineScope(Dispatchers.IO).launch {
                triggeringGeofences.forEach {
                    reminderRepository?.getReminder(it.requestId)
                        ?.let { it1 -> displayNotificationForReminder(it1) }
                }
            }
        }
    }

    private fun displayNotificationForReminder(reminderDataResult: Result<ReminderDataItem?>){
        if (!reminderDataResult.isSuccessful())
            return

        reminderDataResult.getCurrentData()?.let { reminderData ->
            sendNotification(applicationContext, reminderData)
        }
    }

    companion object {
        private const val JOB_ID = 1994

        fun startEnqueueGeofenceTransitionsJob(context: Context?, intent: Intent?) {
            enqueueWork(
                context!!,
                GeofenceTransitionsJobIntentService::class.java,
                JOB_ID,
                intent!!
            )
        }
    }
}