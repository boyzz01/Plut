package com.ardeveloper.plut.view

import android.os.Bundle
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.lifecycle.Observer
import com.ardeveloper.plut.BaseActivity
import com.ardeveloper.plut.R
import com.ardeveloper.plut.data.db.Kota
import com.ardeveloper.plut.databinding.ActivityRegisterUmkmBinding
import com.infield.epcs.utils.Status
import es.dmoral.toasty.Toasty


class RegisterUmkm : BaseActivity() {

    private lateinit var b : ActivityRegisterUmkmBinding
    private lateinit var listKota : List<Kota>
    private lateinit var adapterKota : MutableList<String>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        b = ActivityRegisterUmkmBinding.inflate(layoutInflater)
        val view = b.root
        setContentView(view)

        listKota = ArrayList()
        adapterKota = ArrayList()
        setupViewModel()
        initView()
        getData()


       b.simpan.setOnClickListener {
            if (b.spnKota.selectedItem==null){
                Toasty.error(this,"Mohon pilih kota terlebih dahulu",Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
           if (b.etNama.text!!.isEmpty()){
               Toasty.error(this,"Nama UMKM tidak boleh kosong",Toast.LENGTH_SHORT).show()
               b.etNama.requestFocus()
               return@setOnClickListener
           }

           if (b.etNIB.text!!.isEmpty()){
               Toasty.error(this,"Nomor NIB tidak boleh kosong",Toast.LENGTH_SHORT).show()
               b.etNIB.requestFocus()
               return@setOnClickListener
           }

           uploadData()

       }



    }

    private fun uploadData() {
        val nib = b.etNIB.text.toString().toInt()
        viewModel.addUmkm(b.etNama.text.toString(),nib,b.spnKota.selectedItem.toString()).observe(this, Observer {
            it?.let { resource ->
                when (resource.status) {
                    Status.SUCCESS -> {
                        Log.d("tesDownload","Sukses"+resource.data!!.data)
                        Toasty.success(this,"UMKM Berhasil Ditambahkan").show()
                    }
                    Status.ERROR -> {
                        Log.d("tesDownload","Error "+it.message)

                    }
                    Status.LOADING -> {
                        Log.d("tesDownload",resource.toString())
                    }
                }
            }
        })
    }

    private fun initView() {
        setSupportActionBar(b.toolbar2)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true);
        supportActionBar!!.setDisplayShowHomeEnabled(true);
        val adapter = ArrayAdapter(this, R.layout.dropdown_item, adapterKota)
        b.spnKota.setTitle("Pilih Kabupaten Kota")
        b.spnKota.setPositiveButton("Ok")
        b.spnKota.adapter = adapter


    }

    private fun getData() {
        viewModel.getKota().observe(this, Observer {
            it?.let { resource ->
                when (resource.status) {
                    Status.SUCCESS -> {
                        Log.d("tesDownload","Sukses"+resource.data!!)
                        listKota = resource.data
                        Log.d("tesDownload",""+listKota.size)
                        for(item in listKota){
                            adapterKota.add(item.nama)
                            Log.d("tesDownload",adapterKota[0])
                        }
                        val adapter = ArrayAdapter(this, R.layout.dropdown_item, adapterKota)
                        b.spnKota.adapter = adapter
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