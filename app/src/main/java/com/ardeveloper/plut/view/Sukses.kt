package com.ardeveloper.plut.view

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.ardeveloper.plut.databinding.ActivitySuksesBinding
import com.ardeveloper.plut.utils.Gblvariabel
import java.text.SimpleDateFormat
import java.util.*

class Sukses : AppCompatActivity() {

    private lateinit var b : ActivitySuksesBinding
    private var idtrans = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        b= ActivitySuksesBinding.inflate(layoutInflater)
        val view = b.root
        setContentView(view)
        initView()
        Gblvariabel.from = "1"
    }

    private fun initView() {
        val intent  = intent
        val idtrans = intent.getStringExtra("idtrans")
 //       idtrans = "00001"
        val locale = Locale("id", "ID")
        val sdf = SimpleDateFormat("EEEE, dd MMMM yyyy",locale)
        val currentDate = sdf.format(Date())

        b.tvId.text = idtrans
        b.tvDate.text = currentDate
        Gblvariabel.idback = idtrans
        b.btnEnd.setOnClickListener {
            val intent = Intent(this, DetailTransaksi::class.java)
            intent.putExtra("id",idtrans)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
            startActivity(intent)
            finish()
        }
    }

    override fun onBackPressed() {

        val intent = Intent(this, DetailTransaksi::class.java)
        intent.putExtra("id",idtrans)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
        startActivity(intent)
        finish()
    }
}