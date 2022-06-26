package com.ardeveloper.plut.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.Observer
import com.ardeveloper.plut.BaseActivity
import com.ardeveloper.plut.R
import com.ardeveloper.plut.data.db.Product
import com.ardeveloper.plut.databinding.ActivityOpnameStockBinding
import com.ardeveloper.plut.databinding.ActivityProductDetailBinding
import com.bumptech.glide.Glide
import com.infield.epcs.utils.Status
import es.dmoral.toasty.Toasty

class OpnameStock : BaseActivity() {

    private lateinit var b : ActivityOpnameStockBinding
    private lateinit var kodeProduct : String
    private lateinit var product : Product
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        b = ActivityOpnameStockBinding.inflate(layoutInflater)
        val view = b.root
        setContentView(view)
        initView()
        getData()
    }

    private fun getData() {
        showLoadingDialog()
//
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
                            Toasty.error(this,"Produk Tidak ditemukan").show()
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

        setSupportActionBar(b.toolbar22)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true);
        supportActionBar!!.setDisplayShowHomeEnabled(true);
        supportActionBar!!.title = "Detail Produk"


    }

    private fun setData()
    {
        supportActionBar!!.subtitle = product.nama
        b.kodeProduk.setText(product.kodeProduk)
        b.namaProduk.setText(product.nama)
        b.stockDb.setText(""+product.stock)

        Glide.with(b.imageView15)
            .load(product.foto)
            .error(R.drawable.imgproduct)
            .placeholder(R.drawable.imgproduct)
            .fitCenter()
            .into(b.imageView15)


    }

    override fun onResume() {
        super.onResume()
        getData()
    }
}