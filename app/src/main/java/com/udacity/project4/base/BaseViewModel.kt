package com.udacity.project4.base


import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.udacity.project4.utils.SingleLiveEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

/**
 * Base class for View Models to declare the common LiveData objects in one place
 */
abstract class BaseViewModel : ViewModel() {

    val navigationCommand: SingleLiveEvent<NavigationCommand> = SingleLiveEvent()
    val showErrorMessage: SingleLiveEvent<String> = SingleLiveEvent()
    val showSnackBar: SingleLiveEvent<String> = SingleLiveEvent()
    val showSnackBarInt: SingleLiveEvent<Int> = SingleLiveEvent()
    val showToast: SingleLiveEvent<Int> = SingleLiveEvent()
    val showLoading: SingleLiveEvent<Boolean> = SingleLiveEvent()
    val showNoData: MutableLiveData<Boolean> = MutableLiveData()
}