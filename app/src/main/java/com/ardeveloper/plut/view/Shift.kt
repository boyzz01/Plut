package com.ardeveloper.plut.view

import ApiService
import android.app.SearchManager
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.SearchView
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ardeveloper.plut.BaseActivity
import com.ardeveloper.plut.R
import com.ardeveloper.plut.adapter.HistoryAdapter
import com.ardeveloper.plut.api.ApiClient
import com.ardeveloper.plut.data.response.Transaksi
import com.ardeveloper.plut.databinding.ActivityShiftBinding
import com.ardeveloper.plut.preferences.SharedPrefs
import es.dmoral.toasty.Toasty
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class Shift : BaseActivity(), HistoryAdapter.transaksiAdapterListener {

    private lateinit var apiInterface: ApiService
    private lateinit var b : ActivityShiftBinding

    private lateinit var transaksiList : MutableList<Transaksi>
    private lateinit var mAdapter : HistoryAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        b = ActivityShiftBinding.inflate(layoutInflater)
        setContentView(b.root)

        initView()
        getData()
    }

    private fun getData() {
        showLoadingDialog()
        val id =SharedPrefs.getString(this, SharedPrefs.USERID)
        apiInterface.getTransaksiUser(id).enqueue(object : Callback<List<Transaksi>>{
            override fun onResponse(
                call: Call<List<Transaksi>>,
                response: Response<List<Transaksi>>
            ) {
                if (response.isSuccessful){
                    transaksiList.clear()
                    transaksiList.addAll(response.body()!!)
                    mAdapter.notifyDataSetChanged()
                    hideLoadingDialog()
                }else{
                    Toasty.error(this@Shift,"Error : "+response.code()).show()
                }
            }

            override fun onFailure(call: Call<List<Transaksi>>, t: Throwable) {
                Toasty.error(this@Shift,"Error : "+t.message).show()
                hideLoadingDialog()
            }


        })

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_export, menu);
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId
        return  super.onOptionsItemSelected(item)


    }

    private fun initView() {
        setSupportActionBar(b.toolbar20)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true);
        supportActionBar!!.setDisplayShowHomeEnabled(true);
        supportActionBar!!.title = "List Transaksi "+ SharedPrefs.getString(this, SharedPrefs.USERNAME)
        apiInterface = ApiClient.getClient(this).create(ApiService::class.java)

        transaksiList = ArrayList()
        mAdapter = HistoryAdapter(this,transaksiList,this)

        val mLayoutManager: RecyclerView.LayoutManager = LinearLayoutManager(applicationContext)
        b.rvList.layoutManager = mLayoutManager
        b.rvList.itemAnimator = DefaultItemAnimator()
        b.rvList.adapter = mAdapter
    }

    override fun onContactSelected(transaksi: Transaksi?) {
        val intent = Intent(this, DetailTransaksi::class.java)
        intent.putExtra("id",transaksi!!.idTransaksi)
        startActivity(intent)
    }

    override fun onResume() {
        super.onResume()
        getData()
    }
}