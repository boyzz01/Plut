package com.ardeveloper.plut.view

import ApiService
import android.Manifest
import android.annotation.SuppressLint
import android.bluetooth.BluetoothAdapter
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.DisplayMetrics
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat
import com.ardeveloper.plut.BaseActivity
import com.ardeveloper.plut.R
import com.ardeveloper.plut.api.ApiClient
import com.ardeveloper.plut.data.db.Product
import com.ardeveloper.plut.databinding.ActivityReturBarangBinding
import com.ardeveloper.plut.preferences.SharedPrefs
import com.bumptech.glide.Glide
import com.dantsu.escposprinter.textparser.PrinterTextParserImg
import es.dmoral.toasty.Toasty
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.SimpleDateFormat
import java.util.*


class ReturBarang : BaseActivity() {

    private lateinit var b : ActivityReturBarangBinding
    private lateinit var product : Product
    private var user = ""
    private var umkm = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_retur_barang)

        b = ActivityReturBarangBinding.inflate(layoutInflater)
        val view = b.root
        setContentView(view)
        initView()
        user = SharedPrefs.getString(this,SharedPrefs.USERNAME)
        getData()

    }

    private fun getData() {
        Glide.with(this)
            .load(product.foto)
            .error(R.drawable.image_placeholder)
            .placeholder(R.drawable.image_placeholder).centerCrop()
            .into(b.imageView15)
    }

    private fun initView() {
        val intent = intent
        product = (intent.getSerializableExtra("produk") as? Product)!!
        val nama = product?.nama
        umkm = intent.getStringExtra("umkm").toString()
        setSupportActionBar(b.toolbar5)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true);
        supportActionBar!!.setDisplayShowHomeEnabled(true);
        supportActionBar!!.title = "Retur Produk"
        supportActionBar!!.subtitle = nama

        val ket = arrayOf("Cacat", "Expired", "Tidak Laku")
        b.kodeProduk.setText(product.kodeProduk)
        b.namaProduk.setText(product.nama)
        b.jumStock.setText(""+product.stock)
        val adapter = ArrayAdapter(this, R.layout.dropdown_item, ket)
        b.spnKeterangan.setAdapter(adapter)

        b.button4.setOnClickListener {
            if (b.jumRetur.text.isEmpty()){
                Toasty.error(this,"Mohon isi jumlah retur", Toast.LENGTH_SHORT).show()
                b.jumRetur.requestFocus()
                return@setOnClickListener
            }
            if (b.spnKeterangan.text.isEmpty()){
                Toasty.error(this,"Mohon pilih keterangan terlebih dahulu", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            if (b.jumRetur.text.toString().toInt()>product.stock){
                Toasty.error(this,"Retur melebih stok yang ada", Toast.LENGTH_SHORT).show()
                b.jumRetur.requestFocus()
                return@setOnClickListener
            }

            hideSoftKeyboard(this,it)
            val alert= AlertDialog.Builder(this)
            alert.setTitle("Konfirmasi");
            alert.setMessage("Apakah Data Sudah Benar?");
            alert.setPositiveButton(
                "Ya"
            ) { dialog, which ->
                uploadData()

                dialog.dismiss()
            }

            alert.setNegativeButton(
                "Tidak"
            ) { dialog, which -> dialog.dismiss() }

            alert.show()
        }

    }

    @SuppressLint("MissingPermission")
    private fun cetak() {
        val alert= AlertDialog.Builder(this)
        alert.setTitle("Cetak Struk");
        alert.setMessage("Apakah Anda Ingin Cetak Struk?");
        alert.setPositiveButton(
            "Ya"
        ) { dialog, which ->
            val mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter()
            mBluetoothAdapter.enable()
            pilihan = 2

            val format = SimpleDateFormat(" yyyy-MM-dd  HH:mm:ss")
            dataCetak =   """
         
            [L]
            [C]<b><font size='14'>Struk Retur Produk</font></b>
            [L]
            [C]<u type='double'>${format.format(Date())}</u>
            [C]
            [C]================================
            [L]<b>Nama UMKM :</b>
            [L]${umkm}
            [L]<b>Kode Barang :</b>
            [L]${product.kodeProduk}
            [L]<b>Nama Barang :</b>
            [L]${product.nama}
            [L]<b>Jumlah Retur : </b>
            [L]${b.jumRetur.text}
            [L]<b>Keterangan :</b>
            [L]${b.spnKeterangan.text.toString()}
            [L]
            [C]<b>Pemeriksa</b>
            [C]${user} 
            [C]================================
            """
            if(selectedDevice==null){

                browseBluetoothDevice(2)
            }else{
                printBluetooth(2)
            }
            dialog.dismiss()
        }

        alert.setNegativeButton(
            "Tidak"
        ) { dialog, which -> dialog.dismiss()
        finish()}

        alert.show()
    }





    private fun uploadData() {
        showLoadingDialog()
        val apiInterface = ApiClient.getClient(this).create(ApiService::class.java)


        apiInterface.returProduk(b.kodeProduk.text.toString(),b.jumRetur.text.toString().toInt(),b.spnKeterangan.text.toString(),user)
            .enqueue(object : Callback<ResponseBody> {
                override fun onResponse(
                    call: Call<ResponseBody>,
                    response: Response<ResponseBody>
                ) {
                    if (response.isSuccessful){
                        Toasty.success(this@ReturBarang,"Produk Berhasil Diretur").show()
                        cetak()
                    }else{
                        Toasty.error(this@ReturBarang,"Error : "+response.code()).show()
                    }
                    hideLoadingDialog()
                }

                override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                    Toasty.error(this@ReturBarang,"Error : "+t.message).show()
                    hideLoadingDialog()
                }

            })
    }
}