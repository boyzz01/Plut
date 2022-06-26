package com.ardeveloper.plut


import MainViewModel
import ViewModelFactory
import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.app.ProgressDialog
import android.content.Context
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.util.Base64
import android.util.Log
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import com.ardeveloper.plut.api.RetrofitBuilder
import com.ardeveloper.plut.callback.PermissionCallback
import com.ardeveloper.plut.data.db.DaoSession
import com.ardeveloper.plut.utils.bt.BluetoothUtil
import com.ardeveloper.plut.utils.bt.async.AsyncBluetoothEscPosPrint
import com.ardeveloper.plut.utils.bt.async.AsyncEscPosPrint
import com.ardeveloper.plut.utils.bt.async.AsyncEscPosPrinter
import com.ardeveloper.plut.utils.bt.btconnect
import com.dantsu.escposprinter.connection.DeviceConnection
import com.dantsu.escposprinter.connection.bluetooth.BluetoothConnection
import com.dantsu.escposprinter.connection.bluetooth.BluetoothPrintersConnections
import com.infield.epcs.data.api.ApiHelper
import com.karumi.dexter.Dexter
import com.karumi.dexter.MultiplePermissionsReport
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionDeniedResponse
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.multi.MultiplePermissionsListener
import com.obsez.android.lib.filechooser.ChooserDialog
import es.dmoral.toasty.Toasty
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.ByteArrayOutputStream
import java.io.File
import java.text.DecimalFormat
import java.text.SimpleDateFormat
import java.util.*


open class BaseActivity : AppCompatActivity() {

    /*==============================================================================================
    ======================================BLUETOOTH PART============================================
    ==============================================================================================*/
    val PERMISSION_BLUETOOTH = 1
    val PERMISSION_BLUETOOTH_ADMIN = 2
    val PERMISSION_BLUETOOTH_CONNECT = 3
    val PERMISSION_BLUETOOTH_SCAN = 4
    lateinit var viewModel: MainViewModel
    var db : DaoSession = MainApp.getInstance().daoSession
    private lateinit var progressDialog: AlertDialog
    var pilihan = 0
    var dataCetak = ""

    private lateinit var bluetoothPermission:PermissionCallback
    var selectedDevice: BluetoothConnection? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Set content view, create layout implementation
        setupViewModel()
        setupProgressDialog()


    }


    fun convertToCurrency(value: String): String {
        val currentValue: Double
        currentValue = try {
            java.lang.Double.parseDouble(value)
        } catch (nfe: NumberFormatException) {
            0.0
        }

        return convertToCurrency(currentValue)
    }

    fun convertToCurrency(amount: Double): String {
        val formatter = DecimalFormat("#,###,###")
        return formatter.format(amount).replace(",", ".")
    }

    open fun hideSoftKeyboard(activity: Activity, view: View) {
        val imm: InputMethodManager =
            activity.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(view.applicationWindowToken, 0)
    }

    fun setupViewModel() {
        viewModel = ViewModelProvider(this,  ViewModelFactory(ApiHelper(RetrofitBuilder.apiService))).get(MainViewModel::class.java)
    }

     fun encodeImage(bm: Bitmap): String? {
        val baos = ByteArrayOutputStream()
        bm.compress(Bitmap.CompressFormat.JPEG, 100, baos)
        val b = baos.toByteArray()
        return Base64.encodeToString(b, Base64.DEFAULT)
    }

    private fun setupProgressDialog() {
        val builder = AlertDialog.Builder(this)
        builder.setCancelable(false)
        builder.setView(R.layout.layout_progress_dialog)
        progressDialog = builder.create()
        progressDialog.setCancelable(false)
    }

    fun showLoadingDialog() {
        if (!progressDialog.isShowing) {
            progressDialog.show()
        }
    }

    fun hideLoadingDialog() {
        if (progressDialog.isShowing) {
            progressDialog.dismiss()
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    open fun checkBluetoothPermission(callback: PermissionCallback) {
        Dexter.withActivity(this)
            .withPermissions(Manifest.permission.BLUETOOTH, Manifest.permission.BLUETOOTH_ADMIN)
            .withListener(object : MultiplePermissionsListener {
                @Override
                override fun onPermissionsChecked(report: MultiplePermissionsReport) {
                    val areAllPermissionsGranted: Boolean = report.areAllPermissionsGranted()
                    val isAllPermissionPermanentlyDenied: Boolean = isAllPermissionPermanentlyDenied(report.deniedPermissionResponses)
                    if (areAllPermissionsGranted) {
                        callback.onSuccess()
                    } else if (isAllPermissionPermanentlyDenied) {
                        showMessageOpenBluetooth("Allow to access bluetooth from your device")
                    }
                }

                override fun onPermissionRationaleShouldBeShown(
                    permissions: List<PermissionRequest?>?,
                    token: PermissionToken
                ) {
                    token.continuePermissionRequest()
                }
            }).check()
    }
    open fun isAllPermissionPermanentlyDenied(deniedPermissionResponses: List<PermissionDeniedResponse>): Boolean {
        var hasPermanentlyDeniedAnyPermission = false
        for (deniedResponse in deniedPermissionResponses) {
            hasPermanentlyDeniedAnyPermission = deniedResponse.isPermanentlyDenied
        }
        return hasPermanentlyDeniedAnyPermission
    }

     open fun showMessageOpenBluetooth(msg: String) {
        AlertDialog.Builder(this)
            .setMessage(msg)
            .setPositiveButton(
                "Activate Manual"
            ) { dialog, which -> openBluetooth() }
            .setNegativeButton(
                "Cancel"
            ) { dialog, which -> dialog.dismiss() }
            .show()
    }

     open fun openBluetooth() {
        BluetoothUtil.openBluetooth(this)
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String?>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (grantResults.size > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            when (requestCode) {
                PERMISSION_BLUETOOTH, PERMISSION_BLUETOOTH_ADMIN, PERMISSION_BLUETOOTH_CONNECT, PERMISSION_BLUETOOTH_SCAN -> this.printBluetooth(pilihan)
            }
        }
    }


    fun printBluetooth(pilihan: Int) {
        if (ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.BLUETOOTH
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.BLUETOOTH),
                PERMISSION_BLUETOOTH
            )
        } else if (ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.BLUETOOTH_ADMIN
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.BLUETOOTH_ADMIN),
                PERMISSION_BLUETOOTH_ADMIN
            )
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S && ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.BLUETOOTH_CONNECT
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.BLUETOOTH_CONNECT),
                PERMISSION_BLUETOOTH_CONNECT
            )
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S && ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.BLUETOOTH_SCAN
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.BLUETOOTH_SCAN),
                PERMISSION_BLUETOOTH_SCAN
            )
        } else {
            AsyncBluetoothEscPosPrint(
                this,
                object : AsyncEscPosPrint.OnPrintFinished() {
                    override fun onError(asyncEscPosPrinter: AsyncEscPosPrinter?, codeException: Int) {
                        Log.e(
                            "Async.OnPrintFinished",
                            "AsyncEscPosPrint.OnPrintFinished : An error occurred !"
                        )
                    }

                    override fun onSuccess(asyncEscPosPrinter: AsyncEscPosPrinter?) {
                        Log.i(
                            "Async.OnPrintFinished",
                            "AsyncEscPosPrint.OnPrintFinished : Print is finished !"
                        )
                    }
                }
            )
                .execute(this.getAsyncEscPosPrinter(selectedDevice,pilihan))
        }



    }
    @SuppressLint("MissingPermission")
    open fun browseBluetoothDevice(pilihan: Int) {
        val bluetoothDevicesList = btconnect().list
        if (bluetoothDevicesList != null) {
            val items = arrayOfNulls<String>(bluetoothDevicesList.size + 1)
            items[0] = "Default printer"
            var i = 0
            for (device in bluetoothDevicesList) {

                items[++i] = device.device.name
            }
            val alertDialog = android.app.AlertDialog.Builder(this)
            alertDialog.setTitle("Bluetooth printer selection")
            alertDialog.setItems(
                items
            ) { dialogInterface, i ->
                val index = i - 1
                  if (index == -1) {
                      selectedDevice=   null
                } else {
                      selectedDevice= bluetoothDevicesList[index]
                    printBluetooth(pilihan)
                }

            }
            val alert = alertDialog.create()
            alert.setCanceledOnTouchOutside(false)
            alert.show()
        }
    }
    fun getAsyncEscPosPrinter(printerConnection: DeviceConnection?,pilihan:Int): AsyncEscPosPrinter? {
        val format = SimpleDateFormat("'on' yyyy-MM-dd 'at' HH:mm:ss")
        val printer = AsyncEscPosPrinter(printerConnection, 203, 48f, 32)
        return printer.addTextToPrint(
          dataCetak.trimIndent()
        )
    }

    fun createPartFromString(text: String): RequestBody {
        return RequestBody.create("text/plain".toMediaTypeOrNull(), text)
    }

    fun createPartFromFile(path: String?, key: String): MultipartBody.Part? {
        if (path == null) {
            return null
        }
        val file = File(path)
        val request = RequestBody.create("image/*".toMediaTypeOrNull(), file)
        return MultipartBody.Part.createFormData(key, file.name, request)
    }
}