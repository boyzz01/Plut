package com.ardeveloper.plut.view

import ApiService
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ardeveloper.plut.BaseActivity
import com.ardeveloper.plut.R
import com.ardeveloper.plut.adapter.ReportAdapter
import com.ardeveloper.plut.api.ApiClient
import com.ardeveloper.plut.data.db.Product
import com.ardeveloper.plut.databinding.ActivitySalesFastMovingBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SalesFastMoving : BaseActivity(), ReportAdapter.ItemAdapterListener {

    private lateinit var b : ActivitySalesFastMovingBinding
    private lateinit var adapter : ReportAdapter
    private lateinit var productList : MutableList<Product>
    var judul =""
    var report = ""
    private lateinit var apiInterface: ApiService
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        b = ActivitySalesFastMovingBinding.inflate(layoutInflater)
        setContentView(b.root)

        initView()

        if (report.equals("1")){
            getFast()
        }else if (report.equals("2")){
            getSlow()
        }

    }

    private fun getSlow() {
        showLoadingDialog()
        apiInterface.slowReport().enqueue(object : Callback<List<Product>> {
            override fun onResponse(call: Call<List<Product>>, response: Response<List<Product>>) {
                if (response.isSuccessful){

                    productList.clear()
                    productList.addAll(response.body()!!)
                    adapter.notifyDataSetChanged()

                }
                hideLoadingDialog()
            }

            override fun onFailure(call: Call<List<Product>>, t: Throwable) {
                hideLoadingDialog()
            }

        })
    }

    private fun getFast() {
        showLoadingDialog()
        apiInterface.fastReport().enqueue(object : Callback<List<Product>> {
            override fun onResponse(call: Call<List<Product>>, response: Response<List<Product>>) {
                if (response.isSuccessful){

                    productList.clear()
                    productList.addAll(response.body()!!)
                    adapter.notifyDataSetChanged()

                }
                hideLoadingDialog()
            }

            override fun onFailure(call: Call<List<Product>>, t: Throwable) {
                hideLoadingDialog()
            }

        })
    }

    private fun initView() {

        val intent = intent
        judul = intent.getStringExtra("judul").toString()
        report= intent.getStringExtra("report").toString()
        apiInterface = ApiClient.getClient(this).create(ApiService::class.java)
        setSupportActionBar(b.toolbar23)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true);
        supportActionBar!!.setDisplayShowHomeEnabled(true);
        supportActionBar!!.title = judul

        productList = ArrayList()
        adapter = ReportAdapter(this,productList,this)

        val mLayoutManager: RecyclerView.LayoutManager = LinearLayoutManager(applicationContext)
        b.rvList.layoutManager = mLayoutManager
        b.rvList.itemAnimator = DefaultItemAnimator()
        b.rvList.adapter = adapter
    }

//    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
//        menuInflater.inflate(R.menu.menu_export, menu);
//        return super.onCreateOptionsMenu(menu)
//    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId
        return  super.onOptionsItemSelected(item)


    }

    override fun onItemSelected(product: Product?) {

    }


}