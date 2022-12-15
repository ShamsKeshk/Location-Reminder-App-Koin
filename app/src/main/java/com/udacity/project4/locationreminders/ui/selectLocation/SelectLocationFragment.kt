package com.udacity.project4.locationreminders.ui.selectLocation


import android.Manifest
import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.view.*
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.fragment.app.activityViewModels
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.*
import com.udacity.project4.R
import com.udacity.project4.base.BaseFragment
import com.udacity.project4.base.NavigationCommand
import com.udacity.project4.databinding.FragmentSelectLocationBinding
import com.udacity.project4.locationreminders.domain.viewmodel.SaveReminderViewModel
import com.udacity.project4.utils.setDisplayHomeAsUpEnabled
import java.util.*

class SelectLocationFragment : BaseFragment(), OnMapReadyCallback {

    override val _viewModel: SaveReminderViewModel by activityViewModels()

    private lateinit var binding: FragmentSelectLocationBinding

    private lateinit var mGoogleMap: GoogleMap

    private lateinit var fusedLocationProviderClient: FusedLocationProviderClient

    private val locationPermissionRequest = registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) { permissions ->
        if(permissions.getOrDefault(Manifest.permission.ACCESS_FINE_LOCATION, false) || permissions.getOrDefault(Manifest.permission.ACCESS_COARSE_LOCATION, false)){
            enableMyLocation()
        }else if (permissions.isNotEmpty()){
            _viewModel.showSnackBar.value = getString(R.string.permission_denied_explanation)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        launchRegisterPermission()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentSelectLocationBinding.inflate(inflater, container, false)

       val mapFragment = childFragmentManager.findFragmentById(R.id.fg_google_map) as SupportMapFragment

        mapFragment.getMapAsync(this)

        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(requireActivity())

        binding.viewModel = _viewModel
        binding.lifecycleOwner = this

        setHasOptionsMenu(true)
        setDisplayHomeAsUpEnabled(true)

        return binding.root
    }

    private fun onLocationSelected(pointOfInterest: PointOfInterest) {
        _viewModel.selectedPOI.value = pointOfInterest
        _viewModel.latitude.value = pointOfInterest.latLng.latitude
        _viewModel.longitude.value = pointOfInterest.latLng.longitude
        _viewModel.setSelectedLocation(pointOfInterest.name)

        _viewModel.navigationCommand.value = NavigationCommand.Back
    }


    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.map_options, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem) = when (item.itemId) {
        R.id.normal_map -> {
            mGoogleMap.mapType = GoogleMap.MAP_TYPE_NORMAL
            true
        }
        R.id.hybrid_map -> {
            mGoogleMap.mapType = GoogleMap.MAP_TYPE_HYBRID
            true
        }
        R.id.satellite_map -> {
            mGoogleMap.mapType = GoogleMap.MAP_TYPE_SATELLITE
            true
        }
        R.id.terrain_map -> {
            mGoogleMap.mapType = GoogleMap.MAP_TYPE_TERRAIN
            true
        }
        else -> super.onOptionsItemSelected(item)
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mGoogleMap = googleMap

        addMarkerOnLongClick()
        addPoiClick()
        setMapStyle()
        startListenOnDeviceLocation()
        enableMyLocation(true)
    }

    private fun addMarkerOnLongClick(){
        mGoogleMap.setOnMapLongClickListener {
            val snippet = String.format(Locale.getDefault(),
            getString(R.string.lat_long_snippet,it.latitude,it.longitude))
            mGoogleMap.addMarker(MarkerOptions().position(it)
                .title(getString(R.string.dropped_pin))
                .snippet(snippet)
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE))
            )

            updateCameraZoom(it)

            onLocationSelected(PointOfInterest(it,"custom",getString(R.string.dropped_pin)))
        }
    }

    private fun addPoiClick(){
        mGoogleMap.setOnPoiClickListener {
            val poiMarker = mGoogleMap.addMarker(MarkerOptions().position(it.latLng)
                .title(it.name))

            updateCameraZoom(it.latLng)

            poiMarker?.showInfoWindow()

            onLocationSelected(it)
        }
    }

    private fun setMapStyle(){
        mGoogleMap.setMapStyle(MapStyleOptions.loadRawResourceStyle(requireContext(),R.raw.google_map_style))
    }

    private fun isPermissionGranted(): Boolean{
        return ContextCompat.checkSelfPermission(requireContext(),
            Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED
    }


    @SuppressLint("MissingPermission")
    private fun enableMyLocation(shouldRequest: Boolean = false){
        if (isPermissionGranted()){
            if (::mGoogleMap.isInitialized)
                mGoogleMap.isMyLocationEnabled = true
        }else if (shouldRequest) {
            launchRegisterPermission()
        }
    }

    @SuppressLint("MissingPermission")
    fun launchRegisterPermission(){
        val permissions = mutableListOf<String>().apply {
            add(Manifest.permission.ACCESS_FINE_LOCATION)
            add(Manifest.permission.ACCESS_COARSE_LOCATION)
        }

        locationPermissionRequest.launch(permissions.toTypedArray())
    }


    @SuppressLint("MissingPermission")
    private fun startListenOnDeviceLocation() {
        try {
            if (isPermissionGranted()) {
                val locationResult = fusedLocationProviderClient.lastLocation
                locationResult.addOnCompleteListener(requireActivity()) { task ->
                    if (task.isSuccessful) {
                        // Set the map's camera position to the current location of the device.
                        val lastKnownLocation = task.result
                        if (lastKnownLocation != null) {
                            updateCameraZoom(LatLng(lastKnownLocation.latitude,
                                lastKnownLocation.longitude))
                        }
                    }
                }
            }
        } catch (e: SecurityException) {

        }
    }

    private fun updateCameraZoom(latLng: LatLng){
        mGoogleMap.moveCamera(
            CameraUpdateFactory.newLatLngZoom(latLng, 16f)
        )
    }

}
