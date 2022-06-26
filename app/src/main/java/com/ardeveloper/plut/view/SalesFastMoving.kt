package com.ardeveloper.plut.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import com.ardeveloper.plut.R
import com.ardeveloper.plut.databinding.ActivitySalesFastMovingBinding

class SalesFastMoving : AppCompatActivity() {

    private lateinit var b : ActivitySalesFastMovingBinding
    var judul =""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        b = ActivitySalesFastMovingBinding.inflate(layoutInflater)
        setContentView(b.root)

        initView()
    }

    private fun initView() {

        val intent = intent
        judul = intent.getStringExtra("judul").toString()

        setSupportActionBar(b.toolbar23)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true);
        supportActionBar!!.setDisplayShowHomeEnabled(true);
        supportActionBar!!.title = judul
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_export, menu);
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId
        return  super.onOptionsItemSelected(item)


    }
}