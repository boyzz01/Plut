package com.ardeveloper.plut.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.ardeveloper.plut.R
import com.ardeveloper.plut.databinding.ActivitySalesReportBinding

class SalesReport : AppCompatActivity() {

    private lateinit var b : ActivitySalesReportBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        b = ActivitySalesReportBinding.inflate(layoutInflater)
        setContentView(b.root)

        initView()
    }

    private fun initView() {

        b.btnDate.setOnClickListener {
            val intent = Intent(this,SalesFastMoving::class.java)
            intent.putExtra("judul","Sales By Date")
            startActivity(intent)
        }

        b.fastMoving.setOnClickListener {
            val intent = Intent(this,SalesFastMoving::class.java)
            intent.putExtra("judul","Sales Fast Moving")
            startActivity(intent)
        }

        b.btnSlow.setOnClickListener {
            val intent = Intent(this,SalesFastMoving::class.java)
            intent.putExtra("judul","Sales Slow Moving")
            startActivity(intent)
        }

        b.btnUmkm.setOnClickListener {
            val intent = Intent(this,Laporan::class.java)
            intent.putExtra("judul","Sales By UMKM")
            startActivity(intent)
        }

        b.btnKota.setOnClickListener {
            val intent = Intent(this,SalesFastMoving::class.java)
            intent.putExtra("judul","Sales By Kabupaten/Kota")
            startActivity(intent)
        }

        b.btnKategori.setOnClickListener {
            val intent = Intent(this,SalesFastMoving::class.java)
            intent.putExtra("judul","Sales By Kategori")
            startActivity(intent)
        }

        b.btnPromo.setOnClickListener {
            val intent = Intent(this,SalesFastMoving::class.java)
            intent.putExtra("judul","Sales By Promo")
            startActivity(intent)
        }


    }
}