package com.ardeveloper.plut.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.ardeveloper.plut.R
import com.ardeveloper.plut.databinding.ActivityStockReportBinding

class StockReport : AppCompatActivity() {

    private lateinit var b : ActivityStockReportBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        b = ActivityStockReportBinding.inflate(layoutInflater)
        setContentView(b.root)

        initView()
    }

    private fun initView() {
        b.bydate.setOnClickListener {
            val intent = Intent(this,ReportView::class.java)
            intent.putExtra("report","7");
            intent.putExtra("judul","Stock By Date")
            startActivity(intent)
        }

        b.byumkm.setOnClickListener {
            val intent = Intent(this,ReportView::class.java)
            intent.putExtra("report","8");
            intent.putExtra("judul","Stock By UMKM")
            startActivity(intent)
        }

        b.btnKategori.setOnClickListener {
            val intent = Intent(this,ReportView::class.java)
            intent.putExtra("report","9");
            intent.putExtra("judul","Stock By Kategori")
            startActivity(intent)
        }

        b.btnOutdate.setOnClickListener {
            val intent = Intent(this,ReportView::class.java)
            intent.putExtra("report","10");
            intent.putExtra("judul","Outdate Stock")
            startActivity(intent)
        }

        b.btnZero.setOnClickListener {
            val intent = Intent(this,ReportView::class.java)
            intent.putExtra("report","11");
            intent.putExtra("judul","Stock With Zero Stock")
            startActivity(intent)
        }
    }
}