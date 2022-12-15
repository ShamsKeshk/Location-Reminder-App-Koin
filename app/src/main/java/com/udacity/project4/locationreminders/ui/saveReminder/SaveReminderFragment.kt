package com.udacity.project4.locationreminders.ui.saveReminder

import android.Manifest
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import com.google.android.material.snackbar.Snackbar
import com.udacity.project4.R
import com.udacity.project4.base.BaseFragment
import com.udacity.project4.base.NavigationCommand
import com.udacity.project4.databinding.FragmentSaveReminderBinding
import com.udacity.project4.locationreminders.domain.viewmodel.SaveReminderViewModel
import com.udacity.project4.locationreminders.framework.managers.GeofencingManager
import com.udacity.project4.locationreminders.framework.managers.LocationManager
import com.udacity.project4.utils.PermissionExtensions
import com.udacity.project4.utils.createForegroundAndBackgroundLocationPermissions
import com.udacity.project4.utils.foregroundAndBackgroundLocationPermissionApproved
import com.udacity.project4.utils.setDisplayHomeAsUpEnabled
import org.koin.android.ext.android.get
import org.koin.androidx.viewmodel.ext.android.sharedStateViewModel

class SaveReminderFragment : BaseFragment() {

    override val _viewModel: SaveReminderViewModel by sharedStateViewModel()
    private lateinit var binding: FragmentSaveReminderBinding

    private val locationPermissionRequest = registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) { permissions ->

        var isAccessFineLocationGranted = permissions.getOrDefault(Manifest.permission.ACCESS_FINE_LOCATION, false)

        var isPermissionsGranted = if (PermissionExtensions.isRunningQOrLater()){
            isAccessFineLocationGranted && permissions.getOrDefault(Manifest.permission.ACCESS_BACKGROUND_LOCATION,false)
        }else {
            isAccessFineLocationGranted
        }

        if(isPermissionsGranted){
            locationManager.checkDeviceLocationSettings(true)
        }else {
            _viewModel.showToast.value = R.string.permission_denied_explanation_for_save_reminder
        }
    }

    var mGeofencingManager: GeofencingManager = get()

    private lateinit var locationManager: LocationManager

    private val onDeviceLocationManager = object : LocationManager.DeviceLocationSettingCallback{
        override fun onDeviceLocationSettingEnabled() {
            startAddGeoFence()
        }

        override fun onDeviceLocationSettingFailed(exception: Exception?) {
            if(exception != null){
                _viewModel.showSnackBar.value = "Error getting location settings resolution: ${ exception.message}"
            }else {
                showRequestDeviceLocationSnackBar()
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?): View {
        binding = FragmentSaveReminderBinding.inflate(inflater, container, false)

        locationManager = LocationManager(requireActivity(),this,onDeviceLocationManager)

        setDisplayHomeAsUpEnabled(true)

        binding.viewModel = _viewModel
        binding.lifecycleOwner = this

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.selectLocation.setOnClickListener {
            navigateToSelectLocationScreen()
        }

        binding.saveReminder.setOnClickListener {
            val reminderDataItem = _viewModel.createReminderDataItem()

            if(!_viewModel.validateEnteredData(reminderDataItem))
                return@setOnClickListener

            if (!foregroundAndBackgroundLocationPermissionApproved()){
                locationPermissionRequest.launch(createForegroundAndBackgroundLocationPermissions())
            }else {
                locationManager.checkDeviceLocationSettings(true)
            }
        }
    }

    private fun startAddGeoFence(){
        val reminderDataItem = _viewModel.createReminderDataItem()
        val isDataValid = _viewModel.validateAndSaveReminder(reminderDataItem)
        if (isDataValid){
            mGeofencingManager.startAddSelectedGeofence(reminderDataItem)
        }
    }

    private fun showRequestDeviceLocationSnackBar(){
        Snackbar.make(binding.root,
            R.string.location_required_error,
            Snackbar.LENGTH_INDEFINITE)
            .setAction(android.R.string.ok) {
            locationManager.checkDeviceLocationSettings()
        }.show()
    }

    override fun onDestroy() {
        super.onDestroy()
        //make sure to clear the view model after destroy, as it's a single view model.
        _viewModel.onClear()
    }

    private fun navigateToSelectLocationScreen(){
        _viewModel.navigationCommand.value =
            NavigationCommand.To(SaveReminderFragmentDirections.actionSaveReminderFragmentToSelectLocationFragment())
    }
}
