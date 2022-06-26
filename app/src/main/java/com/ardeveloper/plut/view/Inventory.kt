package com.ardeveloper.plut.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.ardeveloper.plut.BaseActivity
import com.ardeveloper.plut.R
import com.ardeveloper.plut.databinding.ActivityInventoryBinding

class Inventory : BaseActivity() {

    private lateinit var b : ActivityInventoryBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        b = ActivityInventoryBinding.inflate(layoutInflater)
        setContentView(b.root)

        initView()
    }

    private fun initView() {

        setSupportActionBar(b.toolbar19)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.setDisplayShowHomeEnabled(true)

        b.btnAllProduk.setOnClickListener {
            val intent = Intent(this,AllProduk::class.java)
            startActivity(intent)
        }

        b.btnProdukUmkm.setOnClickListener {
            val intent = Intent(this,Umkm::class.java)
            startActivity(intent)
        }

        b.btnStockOpname.setOnClickListener {
            val intent = Intent(this,UmkmStock::class.java)
            startActivity(intent)
        }

    }
}