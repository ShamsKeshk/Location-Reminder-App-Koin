package com.udacity.project4.locationreminders.domain.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.udacity.project4.base.BaseViewModel
import com.udacity.project4.locationreminders.domain.usecases.loadReminders.LoadRemindersUseCase
import com.udacity.project4.locationreminders.framework.model.ReminderDataItem
import com.udacity.project4.locationreminders.framework.model.Result
import kotlinx.coroutines.launch

class RemindersListViewModel constructor(private val loadRemindersUseCase: LoadRemindersUseCase) : BaseViewModel() {
    // list that holds the reminder data to be displayed on the UI
    val remindersList = MutableLiveData<List<ReminderDataItem>>()

    /**
     * Get all the reminders from the DataSource and add them to the remindersList to be shown on the UI,
     * or show error if any
     */
    fun loadReminders() {
        showLoading.value = true
        viewModelScope.launch {
            //interacting with the dataSource has to be through a coroutine
            val result = try {
                val data = loadRemindersUseCase.getReminders()
                Result.Success(data)
            }catch (exception: Exception){
                Result.Error(exception)
            }

            showLoading.postValue(false)
            when (result) {
                is Result.Success<*> -> {
                    remindersList.value = result.getCurrentData()?.getCurrentData()
                }
                is Result.Error ->
                    showSnackBar.value = result.exception?.localizedMessage
                else -> {
                   //Do nothing
                }
            }

            //check if no data has to be shown
            invalidateShowNoData()
        }
    }

    /**
     * Inform the user that there's not any data if the remindersList is empty
     */
    private fun invalidateShowNoData() {
        showNoData.value = remindersList.value == null || remindersList.value!!.isEmpty()
    }
}