package com.ardeveloper.plut.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.ardeveloper.plut.BaseActivity
import com.ardeveloper.plut.R
import com.ardeveloper.plut.databinding.ActivitySettingBinding
import com.ardeveloper.plut.preferences.SharedPrefs

class Setting : BaseActivity() {

    private lateinit var b : ActivitySettingBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        b = ActivitySettingBinding.inflate(layoutInflater)
        setContentView(b.root)

        initView()
        getData()
    }

    private fun getData() {

    }

    private fun initView() {
        setSupportActionBar(b.toolbar21)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true);
        supportActionBar!!.setDisplayShowHomeEnabled(true);
    }
}