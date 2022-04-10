package com.ardeveloper.plut.view

import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.os.Handler
import com.ardeveloper.plut.BaseActivity
import com.ardeveloper.plut.R
import com.ardeveloper.plut.utils.Utils
import es.dmoral.toasty.Toasty

class Splash : BaseActivity() {

    private val MY_PERMISSIONS_REQUEST_ALL = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)



        setupViewModel()
        // we used the postDelayed(Runnable, time) method
        // to send a message with a delayed time.

        Handler().postDelayed({
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if (!Utils.checkAllPermission(this@Splash)) {
                    //call permission call back
                } else {
                    nextActivity()
                }
            }else {
                nextActivity()
            }

        }, 2000) // 3000 is the delayed time in milliseconds.
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String?>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            MY_PERMISSIONS_REQUEST_ALL -> {

                // If request is cancelled, the result arrays are empty.
                if (grantResults.size > 0
                    && grantResults[0] == PackageManager.PERMISSION_GRANTED
                ) {

                    // permission was granted, yay! Do the
                    // contacts-related task you need to do.
                    var grSuccess = 0
                    var i = 0
                    while (i < grantResults.size) {
                        if (grantResults[i] == PackageManager.PERMISSION_GRANTED) {
                            grSuccess++
                        }
                        i++
                    }
                    if (grSuccess == grantResults.size) {
                        nextActivity()
                    } else {
                       Toasty.error(applicationContext, "Permission Denied").show()
                        finish()
                    }
                } else {
                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                    Toasty.error(applicationContext, "Permission Denied").show()
                    finish()
                }
                return
            }
        }
    }
//

    private fun nextActivity() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()
    }
}