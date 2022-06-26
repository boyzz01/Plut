package com.ardeveloper.plut.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.ardeveloper.plut.databinding.ActivityReportBinding

class Report : AppCompatActivity() {

    private lateinit var b : ActivityReportBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        b = ActivityReportBinding.inflate(layoutInflater)
        setContentView(b.root)

        initView()
    }

    private fun initView() {
        b.salesview.setOnClickListener {
            val intent = Intent(this,SalesReport::class.java)
            startActivity(intent)
        }

        b.stockView.setOnClickListener {
            val intent = Intent(this,StockReport::class.java)
            startActivity(intent)
        }
    }
}