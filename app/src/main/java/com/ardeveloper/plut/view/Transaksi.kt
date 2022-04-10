package com.ardeveloper.plut.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.ardeveloper.plut.R
import com.ardeveloper.plut.databinding.ActivityLaporanBinding
import com.ardeveloper.plut.databinding.ActivityTransaksiBinding

class Transaksi : AppCompatActivity() {

    private lateinit var b : ActivityTransaksiBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_transaksi)
        b = ActivityTransaksiBinding.inflate(layoutInflater)
        val view = b.root
        setContentView(view)

        initView()
    }

    private fun initView() {
        TODO("Not yet implemented")
    }
}