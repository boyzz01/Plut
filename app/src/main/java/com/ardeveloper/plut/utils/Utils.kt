package com.ardeveloper.plut.utils

import android.Manifest
import android.app.Activity
import android.content.pm.PackageManager
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat

object Utils {

    private val MY_PERMISSIONS_REQUEST_ALL = 0
    public var permissions: MutableList<String> = ArrayList()


    open fun checkAllPermission(activity: Activity?): Boolean {
        permissions = ArrayList<String>()
       
        checkPermissionBluetooth(activity)
        checkPermissionCameraAndStorage(activity)
        if (!permissions.isEmpty()) {
            ActivityCompat.requestPermissions(
                activity!!,
                permissions.toTypedArray<String>(),
                MY_PERMISSIONS_REQUEST_ALL
            )
            return false
        }
        return true
    }

    fun checkPermissionCameraAndStorage(activity: Activity?) {
        //Camera
        if (ContextCompat.checkSelfPermission(
                activity!!,
                Manifest.permission.CAMERA
            )
            != PackageManager.PERMISSION_GRANTED
        ) {
            permissions.add(Manifest.permission.CAMERA)
        }
        //READ_EXTERNAL_STORAGE
        if (ContextCompat.checkSelfPermission(
                activity,
                Manifest.permission.READ_EXTERNAL_STORAGE
            )
            != PackageManager.PERMISSION_GRANTED
        ) {
            permissions.add(Manifest.permission.READ_EXTERNAL_STORAGE)
        }

        //WRITE_EXTERNAL_STORAGE
        if (ContextCompat.checkSelfPermission(
                activity,
                Manifest.permission.WRITE_EXTERNAL_STORAGE
            )
            != PackageManager.PERMISSION_GRANTED
        ) {
            permissions.add(Manifest.permission.WRITE_EXTERNAL_STORAGE)
        }
    }

    fun checkPermissionBluetooth(activity: Activity?) {
        //BLUETOOTH
        if (ContextCompat.checkSelfPermission(
                activity!!,
                Manifest.permission.BLUETOOTH
            )
            != PackageManager.PERMISSION_GRANTED
        ) {
           permissions.add(Manifest.permission.BLUETOOTH)
        }

        //BLUETOOTH_ADMIN
        if (ContextCompat.checkSelfPermission(
                activity,
                Manifest.permission.BLUETOOTH_ADMIN
            )
            != PackageManager.PERMISSION_GRANTED
        ) {
            permissions.add(Manifest.permission.BLUETOOTH_ADMIN)
        }
    }

}
