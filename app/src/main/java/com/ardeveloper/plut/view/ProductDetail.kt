package com.ardeveloper.plut.view

import ApiService
import android.annotation.SuppressLint
import android.bluetooth.BluetoothAdapter
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AlertDialog
import com.ardeveloper.plut.BaseActivity
import com.ardeveloper.plut.R
import com.ardeveloper.plut.databinding.ActivityProductDetailBinding
import com.bumptech.glide.Glide
import java.text.SimpleDateFormat
import java.util.*
import androidx.lifecycle.Observer
import com.ardeveloper.plut.api.ApiClient
import com.ardeveloper.plut.data.db.Product
import com.ardeveloper.plut.data.db.UMKM
import com.ardeveloper.plut.data.model.DetailProductResponse
import com.infield.epcs.utils.Status
import es.dmoral.toasty.Toasty
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ProductDetail : BaseActivity() {

    private lateinit var b : ActivityProductDetailBinding
    private lateinit var kodeProduct : String
    private lateinit var product : Product

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        b = ActivityProductDetailBinding.inflate(layoutInflater)
        val view = b.root
        setContentView(view)
        initView()
        getData()
    }

    private fun getData() {
        showLoadingDialog()
//        val apiInterface = ApiClient.getClient(this).create(ApiService::class.java)
//
//        apiInterface.getProdukbyId(kodeProduct).enqueue(object : Callback<List<Product>>{
//            override fun onResponse(call: Call<List<Product>>, response: Response<List<Product>>) {
//                product = response.body().
//                hideLoadingDialog()
//                if (product!=null){
//                    setData()
//
//                }else{
//
//                    Toasty.error(this@ProductDetail,"Produk Tidak ditemukan").show()
//                    finish()
//                }
//
//            }
//
//            override fun onFailure(call: Call<List<Product>>, t: Throwable) {
//                hideLoadingDialog()
//                Toasty.error(this@ProductDetail,"Error"+t.message).show()
//                finish()
//            }
//
//        })
        viewModel.getProdukDetail(kodeProduct).observe(this, Observer {
            it?.let { resource ->
                when (resource.status) {
                    Status.SUCCESS -> {
                        product = resource.data!!
                        hideLoadingDialog()
                        if (product!=null)
                        {
                         setData()
                        }else{
                            Toasty.error(this@ProductDetail,"Produk Tidak ditemukan").show()
                            finish()
                        }
                    }
                    Status.ERROR -> {
                        Toasty.error(this,"Produk Tidak ditemukan Error : "+it.message).show()
                        Log.d("tesDownload","Error"+it.message)
                        hideLoadingDialog()
                        finish()
                    }
                    Status.LOADING -> {
                        Log.d("tesDownload",resource.toString())
                    }
                }
            }
        })


    }

    private fun initView() {
        val intent = intent
        kodeProduct = intent.getStringExtra("produk").toString()

        setSupportActionBar(b.toolbar8)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true);
        supportActionBar!!.setDisplayShowHomeEnabled(true);
        supportActionBar!!.title = "Detail Produk"


    }

    private fun setData()
    {
        supportActionBar!!.subtitle = product.nama
        b.kodeProduk.setText(product.kodeProduk)
        b.namaProduk.setText(product.nama)
        b.jumStock.setText(""+product.stock)
        b.hargaProduk.setText(""+product.harga)
        b.namaUmkm.setText(product.namaUmkm)
        Glide.with(b.imageView15)
            .load(product.foto)
            .error(R.drawable.imgproduct)
            .placeholder(R.drawable.imgproduct)
            .fitCenter()
            .into(b.imageView15)

        b.menuPrint.setOnClickListener{
            cetak()
        }

        b.menuRetur.setOnClickListener{
            val intent = Intent(this, ReturBarang::class.java)
            intent.putExtra("umkm", product.namaUmkm)
            intent.putExtra("produk", product)
            startActivity(intent)
        }

        b.menuEdit.setOnClickListener{
            val intent = Intent(this, OpnameBarang::class.java)
            intent.putExtra("umkm", product.namaUmkm)
            intent.putExtra("produk", product)
            startActivity(intent)
        }
    }

    override fun onResume() {
        super.onResume()
        getData()
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

            val bar = product.kodeProduk
            Log.d("barcode","${product.kodeProduk}")
            val format = SimpleDateFormat(" yyyy-MM-dd  HH:mm:ss")
            dataCetak =   """
            [L]
            [C]<b><font size='14'>${product.nama}</font></b>
            [L]
            [C]<b><font size='14'>${product.kodeProduk}</font></b>
            [C]================================
            [L]<b>Nama UMKM :</b>
            [L]${product.namaUmkm}
            [L]<b>Harga : </b>
            [L]${product.harga.toString()}
            [L]
            [C]<qrcode size='20'>$bar</qrcode>
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
        ) { dialog, which -> dialog.dismiss() }

        alert.show()
    }
}