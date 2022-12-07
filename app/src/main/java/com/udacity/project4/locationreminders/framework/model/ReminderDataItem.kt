package com.udacity.project4.locationreminders.framework.model

import android.os.Parcelable
import com.udacity.project4.locationreminders.framework.local.data.ReminderDataEntity
import kotlinx.parcelize.Parcelize
import java.io.Serializable
import java.util.*

/**
 * data class acts as a data mapper between the DB and the UI
 */
@Parcelize
data class ReminderDataItem(
    var title: String?,
    var description: String?,
    var location: String?,
    var latitude: Double?,
    var longitude: Double?,
    val id: String = UUID.randomUUID().toString()
) : Parcelable {

    fun asEntity(): ReminderDataEntity{
        return ReminderDataEntity(
            title,
            description,
            location,
            latitude,
            longitude,
            id
        )
    }
}