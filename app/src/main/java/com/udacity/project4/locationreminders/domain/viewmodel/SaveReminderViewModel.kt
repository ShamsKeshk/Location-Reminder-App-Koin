package com.udacity.project4.locationreminders.domain.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.google.android.gms.maps.model.PointOfInterest
import com.udacity.project4.R
import com.udacity.project4.base.BaseViewModel
import com.udacity.project4.base.NavigationCommand
import com.udacity.project4.locationreminders.domain.usecases.saveReminder.SaveReminderUseCase
import com.udacity.project4.locationreminders.framework.model.ReminderDataItem
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SaveReminderViewModel @Inject constructor(
    private val saveReminderUseCase: SaveReminderUseCase
) : BaseViewModel() {

    val reminderTitle = MutableLiveData<String?>()
    val reminderDescription = MutableLiveData<String?>()
    val reminderSelectedLocationStr = MutableLiveData<String?>()
    val selectedPOI = MutableLiveData<PointOfInterest?>()
    val latitude = MutableLiveData<Double?>()
    val longitude = MutableLiveData<Double?>()

    /**
     * Clear the live data objects to start fresh next time the view model gets called
     */
    fun onClear() {
        reminderTitle.value = null
        reminderDescription.value = null
        reminderSelectedLocationStr.value = null
        selectedPOI.value = null
        latitude.value = null
        longitude.value = null
    }

    fun setTitle(title: String){
        reminderTitle.value = title
    }

    fun setDescription(description: String){
        reminderDescription.value = description
    }

    fun setSelectedLocation(selectedLocation: String){
        reminderSelectedLocationStr.value = selectedLocation
    }

    /**
     * Validate the entered data then saves the reminder data to the DataSource
     */
    fun validateAndSaveReminder(reminderData: ReminderDataItem) {
        if (validateEnteredData(reminderData)) {
            saveReminder(reminderData)
        }
    }

    /**
     * Save the reminder to the data source
     */
    fun saveReminder(reminderData: ReminderDataItem) {
        showLoading.value = true
        viewModelScope.launch {
            saveReminderUseCase.saveReminder(reminderData)
            showLoading.value = false
            showToast.value = R.string.reminder_saved
            navigationCommand.value = NavigationCommand.Back
        }
    }

    /**
     * Validate the entered data and show error to the user if there's any invalid data
     */
    fun validateEnteredData(reminderData: ReminderDataItem): Boolean {
        if (reminderData.title.isNullOrEmpty()) {
            showSnackBarInt.value = R.string.err_enter_title
            return false
        }

        if (reminderData.location.isNullOrEmpty()) {
            showSnackBarInt.value = R.string.err_select_location
            return false
        }
        return true
    }

    fun createReminderDataItem(): ReminderDataItem{
        val title = reminderTitle.value
        val description = reminderDescription.value
        val location = reminderSelectedLocationStr.value
        val latitude = latitude.value
        val longitude = longitude.value

        return ReminderDataItem(title, description,location, latitude,longitude)
    }
}