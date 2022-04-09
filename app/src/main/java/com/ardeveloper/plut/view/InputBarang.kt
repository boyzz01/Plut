package com.ardeveloper.plut.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.lifecycle.Observer
import com.ardeveloper.plut.BaseActivity
import com.ardeveloper.plut.R
import com.ardeveloper.plut.api.RetrofitBuilder
import com.ardeveloper.plut.data.db.Kategori
import com.ardeveloper.plut.data.db.UMKM
import com.ardeveloper.plut.databinding.ActivityInputBarangBinding
import com.ardeveloper.plut.databinding.ActivityUmkmBinding
import com.infield.epcs.utils.Status
import es.dmoral.toasty.Toasty
import okhttp3.ResponseBody
import retrofit2.Callback
import retrofit2.Call
import retrofit2.Response

class InputBarang : BaseActivity() {
    
    private lateinit var b : ActivityInputBarangBinding
    private lateinit var kategoriList : MutableList<Kategori>
    private lateinit var adapterKategori : MutableList<String>
    private lateinit var umkm : String
    private lateinit var kota : String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        b = ActivityInputBarangBinding.inflate(layoutInflater)
        val view = b.root
        setContentView(view)
        init()
        getData()

        b.button2.setOnClickListener {
            if (b.spnKategori.selectedItem==null){
                Toasty.error(this,"Mohon pilih kategori terlebih dahulu", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            if (b.nama.text!!.isEmpty()){
                Toasty.error(this,"Nama Produk tidak boleh kosong",Toast.LENGTH_SHORT).show()
                b.nama.requestFocus()
                return@setOnClickListener
            }
            if (b.harga.text!!.isEmpty()){
                Toasty.error(this,"Harga tidak boleh kosong",Toast.LENGTH_SHORT).show()
                b.harga.requestFocus()
                return@setOnClickListener
            }
            if (b.stock.text!!.isEmpty()){
                Toasty.error(this,"Stock tidak boleh kosong",Toast.LENGTH_SHORT).show()
                b.stock.requestFocus()
                return@setOnClickListener
            }

            uploadData()
        }
    }

    private fun uploadData() {

        RetrofitBuilder.apiService.addProduk(
            b.nama.text.toString(),
            b.harga.text.toString().toInt(),
            b.spnKategori.selectedItem.toString(),
            umkm,
            kota,
            b.stock.text.toString().toInt()
        ).enqueue(object : Callback<ResponseBody> {
            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
               Log.d("tes","sukses"+response.message())
            }

            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                Log.d("tes",""+t.message)
            }

        })
    }

    private fun init(){
        val intent = intent
        val nama = intent.getStringExtra("nama")

        umkm = intent.getIntExtra("id",0).toString()
        kota = intent.getStringExtra("kota").toString()
        setSupportActionBar(b.toolbar3)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true);
        supportActionBar!!.setDisplayShowHomeEnabled(true);
        supportActionBar!!.title = nama
        kategoriList = ArrayList()
        adapterKategori = ArrayList()
        val adapter = ArrayAdapter(this, R.layout.dropdown_item, adapterKategori)
        b.spnKategori.setTitle("Pilih Kategori Barang")
        b.spnKategori.setPositiveButton("Ok")
        b.spnKategori.adapter = adapter
    }
    private fun getData() {
        setupViewModel()
        viewModel.getKategori().observe(this, Observer {
            it?.let { resource ->
                when (resource.status) {
                    Status.SUCCESS -> {
                        kategoriList.clear()
                        kategoriList.addAll(resource.data!!)
                        for(item in kategoriList){
                            adapterKategori.add(item.nama)
                            Log.d("tesDownload",adapterKategori[0])
                        }
                        val adapter = ArrayAdapter(this, R.layout.dropdown_item, adapterKategori)
                        b.spnKategori.adapter = adapter
                        adapter.notifyDataSetChanged()

                    }
                    Status.ERROR -> {
                        Log.d("tesDownload","Error"+it.message)

                    }
                    Status.LOADING -> {
                        Log.d("tesDownload",resource.toString())
                    }
                }
            }
        })

    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}