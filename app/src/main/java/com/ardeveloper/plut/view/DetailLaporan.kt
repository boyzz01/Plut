package com.ardeveloper.plut.view

import ApiService
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ardeveloper.plut.BaseActivity
import com.ardeveloper.plut.R
import com.ardeveloper.plut.adapter.DetailAdapter
import com.ardeveloper.plut.adapter.DetailLaporanAdapter
import com.ardeveloper.plut.api.ApiClient
import com.ardeveloper.plut.data.response.TransaksiItem
import com.ardeveloper.plut.databinding.ActivityDetailLaporanBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailLaporan : BaseActivity() {

    private lateinit var b : ActivityDetailLaporanBinding
    private lateinit var item: MutableList<TransaksiItem>
    var adapter : DetailLaporanAdapter? = null
    private var idtrans = ""
    private lateinit var apiInterface: ApiService

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        b= ActivityDetailLaporanBinding.inflate(layoutInflater)
        setContentView(b.root)
        initView()
        getData()
    }

    private fun initView() {
        item = ArrayList()
        idtrans = intent.getStringExtra("id")!!
        Log.d("tes",idtrans)
        //  transid = intent.getStringExtra("idtrans")!!
        apiInterface = ApiClient.getClient(this).create(ApiService::class.java)
        setSupportActionBar(b.toolbar15)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true);
        supportActionBar!!.setDisplayShowHomeEnabled(true);
        supportActionBar!!.title = "Sales By UMKM"

        adapter = DetailLaporanAdapter()
        val mLayoutManager: RecyclerView.LayoutManager = LinearLayoutManager(applicationContext)
        b.rv.layoutManager = mLayoutManager // set LayoutManager to rvCartView
        b.rv.setHasFixedSize(true)
        b.rv.itemAnimator = DefaultItemAnimator()
        b.rv.adapter = adapter
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_export, menu);
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId
        return  super.onOptionsItemSelected(item)


    }

    private fun getData() {
        showLoadingDialog()
        apiInterface.getLaporanDetail(idtrans).enqueue(object : Callback<List<TransaksiItem>> {
            override fun onResponse(call: Call<List<TransaksiItem>>, response: Response<List<TransaksiItem>>) {
                if (response.isSuccessful){

                    item.addAll(response.body()!!)

                    adapter!!.clearAdapter()
                    adapter!!.setItems(item)
                    adapter!!.notifyDataSetChanged()

                }
                hideLoadingDialog()
            }

            override fun onFailure(call: Call<List<TransaksiItem>>, t: Throwable) {
                hideLoadingDialog()
            }

        })
    }
}