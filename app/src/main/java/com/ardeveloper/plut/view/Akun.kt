package com.ardeveloper.plut.view

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.ardeveloper.plut.BaseActivity
import com.ardeveloper.plut.databinding.ActivityAkunBinding
import com.ardeveloper.plut.preferences.SharedPrefs

class Akun : BaseActivity() {

    private lateinit var b : ActivityAkunBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        b = ActivityAkunBinding.inflate(layoutInflater)
        val view = b.root
        setContentView(view)


        setSupportActionBar(b.toolbar10)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true);
        supportActionBar!!.setDisplayShowHomeEnabled(true);

        b.tvName.setText(SharedPrefs.getString(this,SharedPrefs.USERNAME))
        b.btnLogout.setOnClickListener{
            SharedPrefs.save(this, SharedPrefs.LOGIN,false)
            SharedPrefs.save(this, SharedPrefs.USERNAME,"")
            SharedPrefs.save(this, SharedPrefs.USER_LEVEL,"")
            val intent = Intent(this, MainActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
            startActivity(intent)
            finish()
        }
    }

}