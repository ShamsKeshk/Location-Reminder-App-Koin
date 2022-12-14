package com.udacity.project4.locationreminders.ui.saveReminder

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.udacity.project4.base.BaseFragment
import com.udacity.project4.base.NavigationCommand
import com.udacity.project4.databinding.FragmentSaveReminderBinding
import com.udacity.project4.locationreminders.domain.viewmodel.SaveReminderViewModel
import com.udacity.project4.locationreminders.framework.managers.GeofencingManager
import com.udacity.project4.utils.setDisplayHomeAsUpEnabled
import org.koin.android.ext.android.get
import org.koin.androidx.viewmodel.ext.android.sharedStateViewModel

class SaveReminderFragment : BaseFragment() {

    override val _viewModel: SaveReminderViewModel by sharedStateViewModel()
    private lateinit var binding: FragmentSaveReminderBinding

    var mGeofencingManager: GeofencingManager = get()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?): View {
        binding = FragmentSaveReminderBinding.inflate(inflater, container, false)

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
            val isDataValid = _viewModel.validateAndSaveReminder(reminderDataItem)
            if (isDataValid){
                mGeofencingManager.startAddSelectedGeofence(reminderDataItem)
            }
        }
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
