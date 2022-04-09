package com.ardeveloper.plut

import MainViewModel
import ViewModelFactory
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.ardeveloper.plut.data.db.DaoSession


import com.infield.epcs.data.api.ApiHelper
import com.ardeveloper.plut.api.RetrofitBuilder

open class BaseActivity : AppCompatActivity() {

    lateinit var viewModel: MainViewModel
    var db : DaoSession = MainApp.getInstance().daoSession

    fun setupViewModel() {
        viewModel = ViewModelProvider(this,  ViewModelFactory(ApiHelper(RetrofitBuilder.apiService))).get(MainViewModel::class.java)
    }
}