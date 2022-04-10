package com.ardeveloper.plut.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.ardeveloper.plut.BaseActivity
import com.ardeveloper.plut.R
import com.ardeveloper.plut.databinding.ActivityAllTransaksiBinding

class AllTransaksi : BaseActivity() {

    private lateinit var b : ActivityAllTransaksiBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        b = ActivityAllTransaksiBinding.inflate(layoutInflater)

        val view = b.root
        setContentView(view)

        initView()
    }

    private fun initView() {
        setSupportActionBar(b.toolbar12)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true);
        supportActionBar!!.setDisplayShowHomeEnabled(true);
    }
}