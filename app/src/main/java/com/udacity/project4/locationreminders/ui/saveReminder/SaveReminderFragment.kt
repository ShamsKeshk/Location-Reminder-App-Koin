package com.udacity.project4.locationreminders.ui.saveReminder

import android.Manifest
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.activityViewModels
import com.udacity.project4.base.BaseFragment
import com.udacity.project4.base.NavigationCommand
import com.udacity.project4.databinding.FragmentSaveReminderBinding
import com.udacity.project4.locationreminders.domain.viewmodel.SaveReminderViewModel
import com.udacity.project4.locationreminders.framework.managers.GeofencingManager
import com.udacity.project4.utils.setDisplayHomeAsUpEnabled
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class SaveReminderFragment : BaseFragment() {

    override val _viewModel: SaveReminderViewModel by activityViewModels()
    private lateinit var binding: FragmentSaveReminderBinding

    @Inject
    lateinit var mGeofencingManager: GeofencingManager

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?): View {
        binding = FragmentSaveReminderBinding.inflate(inflater, container, false)

        setDisplayHomeAsUpEnabled(true)

        binding.viewModel = _viewModel
        binding.lifecycleOwner = this

        return binding.root
    }

    private val locationPermissionRequest = registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) { permissions ->
        when {
            permissions.getOrDefault(Manifest.permission.ACCESS_FINE_LOCATION, false) -> {
                _viewModel.navigationCommand.value =
                    NavigationCommand.To(SaveReminderFragmentDirections.actionSaveReminderFragmentToSelectLocationFragment())
            }
            permissions.getOrDefault(Manifest.permission.ACCESS_COARSE_LOCATION, false) -> {
                _viewModel.navigationCommand.value =
                    NavigationCommand.To(SaveReminderFragmentDirections.actionSaveReminderFragmentToSelectLocationFragment())
            } else -> {

        }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.selectLocation.setOnClickListener {
            launchRegisterPermission()
        }

        binding.saveReminder.setOnClickListener {
            val reminderDataItem = _viewModel.createReminderDataItem()
            mGeofencingManager.startAddSelectedGeofence(reminderDataItem)
            _viewModel.validateAndSaveReminder(reminderDataItem)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        //make sure to clear the view model after destroy, as it's a single view model.
        _viewModel.onClear()
    }

    private fun launchRegisterPermission(){
        locationPermissionRequest.launch(arrayOf(
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION))
    }
}
