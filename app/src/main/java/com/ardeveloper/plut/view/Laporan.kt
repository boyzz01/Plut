package com.ardeveloper.plut.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.ardeveloper.plut.BaseActivity
import com.ardeveloper.plut.R
import com.ardeveloper.plut.databinding.ActivityLaporanBinding

class Laporan : BaseActivity() {

    private lateinit var b : ActivityLaporanBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        b = ActivityLaporanBinding.inflate(layoutInflater)
        val view = b.root
        setContentView(view)

        initView()
    }

    private fun initView() {
        setSupportActionBar(b.toolbar11)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true);
        supportActionBar!!.setDisplayShowHomeEnabled(true);
    }
}