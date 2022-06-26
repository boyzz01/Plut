package com.ardeveloper.plut.view

import android.app.SearchManager
import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.os.Handler
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.SearchView
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ajts.androidmads.library.SQLiteToExcel
import com.ardeveloper.plut.BaseActivity
import com.ardeveloper.plut.R
import com.ardeveloper.plut.adapter.HistoryAdapter
import com.ardeveloper.plut.adapter.ProductUmkmAdapter
import com.ardeveloper.plut.data.db.Product
import com.ardeveloper.plut.data.response.Transaksi
import com.ardeveloper.plut.data.service.AllDbService
import com.ardeveloper.plut.databinding.ActivityAllTransaksiBinding
import com.ardeveloper.plut.utils.Helper
import com.infield.epcs.utils.Status
import es.dmoral.toasty.Toasty
import java.io.File
import java.text.SimpleDateFormat

class AllTransaksi : BaseActivity(), HistoryAdapter.transaksiAdapterListener {

    private var searchView: SearchView? = null
    private lateinit var transaksiList : MutableList<Transaksi>
    private lateinit var mAdapter : HistoryAdapter

    private lateinit var b : ActivityAllTransaksiBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        b = ActivityAllTransaksiBinding.inflate(layoutInflater)

        val view = b.root
        setContentView(view)
        initView()
        getData()
    }

    private fun getData() {
        showLoadingDialog()
        viewModel.getHistory().observe(this, Observer {
            it?.let { resource ->
                when (resource.status) {
                    Status.SUCCESS -> {
                        transaksiList.clear()
                        transaksiList.addAll(resource.data!!)
                        mAdapter!!.notifyDataSetChanged()
                        hideLoadingDialog()
                    }
                    Status.ERROR -> {
                        Log.d("tesDownload","Error"+it.message)
                        hideLoadingDialog()
                    }
                    Status.LOADING -> {
                        Log.d("tesDownload",resource.toString())
                    }
                }
            }
        })
    }

    private fun initView() {
        setSupportActionBar(b.toolbar12)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true);
        supportActionBar!!.setDisplayShowHomeEnabled(true);

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

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_umkm2, menu);

        val searchManager = getSystemService(Context.SEARCH_SERVICE) as SearchManager
        searchView = menu!!.findItem(R.id.action_search)
            .actionView as SearchView
        searchView!!.setSearchableInfo(
            searchManager
                .getSearchableInfo(componentName)
        )
        searchView!!.maxWidth = Int.MAX_VALUE

        val test = menu!!.findItem(R.id.export)
        test.isVisible = false
        searchView!!.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                // filter recycler view when query submitted
                mAdapter.filter.filter(query)
                return false
            }

            override fun onQueryTextChange(query: String): Boolean {
                // filter recycler view when text is changed
                mAdapter.filter.filter(query)
                return false
            }
        })
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId

        //noinspection SimplifiableIfStatement

        if (id == R.id.action_search) {
            return  true
        } else if (id==R.id.refresh){
            getData()
        }else if (id==R.id.export){
           // folderChooser()
        }
        return  super.onOptionsItemSelected(item)


    }

    override fun onBackPressed() {
        // close search view on back button pressed
        if (!searchView!!.isIconified()) {
            searchView!!.isIconified = true
            return
        }else{
            super.onBackPressed()
        }
    }

    override fun onResume() {
        super.onResume()
        getData()
    }




}