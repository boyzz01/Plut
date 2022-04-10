package com.ardeveloper.plut.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.ardeveloper.plut.BaseActivity
import com.ardeveloper.plut.R
import com.ardeveloper.plut.data.db.Product
import com.ardeveloper.plut.databinding.ActivityOpnameBarangBinding
import com.bumptech.glide.Glide

class OpnameBarang : BaseActivity() {

    private lateinit var b : ActivityOpnameBarangBinding
    private lateinit var product : Product
    private var umkm = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        b = ActivityOpnameBarangBinding.inflate(layoutInflater)
        val view = b.root
        setContentView(view)

        initView()
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
        setSupportActionBar(b.toolbar4)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true);
        supportActionBar!!.setDisplayShowHomeEnabled(true);
        supportActionBar!!.title = "Opname Produk"

        b.kodeProduk.setText(product.kodeProduk)
        b.namaProduk.setText(product.nama)
        b.jumStock.setText(""+product.stock)
        b.hargaProduk.setText(""+product.harga)
        b.namaUmkm.setText(""+product.namaUmkm)

        b.txtChooseImage3.setOnClickListener {

        }
    }
}