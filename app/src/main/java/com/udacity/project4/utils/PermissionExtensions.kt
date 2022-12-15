package com.udacity.project4.utils

import android.Manifest
import android.annotation.TargetApi
import android.content.pm.PackageManager
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment

class PermissionExtensions {

    companion object{

        fun isRunningQOrLater() = android.os.Build.VERSION.SDK_INT >=
                android.os.Build.VERSION_CODES.Q
    }
}

@TargetApi(29)
fun Fragment.foregroundAndBackgroundLocationPermissionApproved(): Boolean {
    return isForegroundLocationApproved() && isBackgroundPermissionApproved()
}

private fun Fragment.isForegroundLocationApproved(): Boolean{
    return (PackageManager.PERMISSION_GRANTED ==
            ActivityCompat.checkSelfPermission(this.requireContext(), Manifest.permission.ACCESS_FINE_LOCATION))
}

private fun Fragment.isBackgroundPermissionApproved(): Boolean{
    return if (PermissionExtensions.isRunningQOrLater()) {
        PackageManager.PERMISSION_GRANTED ==
                ActivityCompat.checkSelfPermission(
                    this.requireContext(), Manifest.permission.ACCESS_BACKGROUND_LOCATION
                )
    } else {
        true
    }
}

@TargetApi(29 )
fun Fragment.createForegroundAndBackgroundLocationPermissions(): Array<String> {

    var permissionsArray = arrayOf(Manifest.permission.ACCESS_FINE_LOCATION)
    when {
        PermissionExtensions.isRunningQOrLater() -> {
            permissionsArray += Manifest.permission.ACCESS_BACKGROUND_LOCATION

        }
    }

    return permissionsArray
}