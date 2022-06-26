package com.ardeveloper.plut.view

import android.app.SearchManager
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.SearchView
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ardeveloper.plut.BaseActivity
import com.ardeveloper.plut.R
import com.ardeveloper.plut.adapter.LaporanAdapter
import com.ardeveloper.plut.adapter.UmkmAdapter
import com.ardeveloper.plut.data.db.UMKM
import com.ardeveloper.plut.data.response.LaporanUmkm
import com.ardeveloper.plut.databinding.ActivityLaporanBinding
import com.ardeveloper.plut.databinding.ActivityUmkmBinding
import com.infield.epcs.utils.Status

class Laporan : BaseActivity(), LaporanAdapter.UmkmAdapterListener {

    private lateinit var b : ActivityLaporanBinding

    private var searchView: SearchView? = null
    private lateinit var umkmList : MutableList<LaporanUmkm>
    private lateinit var mAdapter : LaporanAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        b = ActivityLaporanBinding.inflate(layoutInflater)
        val view = b.root
        setContentView(view)

        initView()
        getData()
    }

    private fun initView() {
        setSupportActionBar(b.toolbar11)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true);
        supportActionBar!!.setDisplayShowHomeEnabled(true);

        umkmList = ArrayList()
        mAdapter = LaporanAdapter(this,umkmList,this)
        val mLayoutManager: RecyclerView.LayoutManager = LinearLayoutManager(applicationContext)
        b.rv.layoutManager = mLayoutManager
        b.rv.itemAnimator = DefaultItemAnimator()
        b.rv.adapter = mAdapter
    }

    override fun onResume() {
        super.onResume()
        getData()
    }

    private fun getData() {
        showLoadingDialog()

        viewModel.getLaporan().observe(this, Observer {
            it?.let { resource ->
                when (resource.status) {
                    Status.SUCCESS -> {
                        umkmList.clear()
                        umkmList.addAll(resource.data!!)
                        mAdapter.notifyDataSetChanged()
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

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_umkm, menu);

        val searchManager = getSystemService(Context.SEARCH_SERVICE) as SearchManager
        searchView = menu!!.findItem(R.id.action_search)
            .actionView as SearchView
        searchView!!.setSearchableInfo(
            searchManager
                .getSearchableInfo(componentName)
        )
        searchView!!.maxWidth = Int.MAX_VALUE

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
        }
        return  super.onOptionsItemSelected(item)


    }

    override fun onBackPressed() {
        // close search view on back button pressed
        if (!searchView!!.isIconified()) {
            searchView!!.isIconified = true
            return
        }
        super.onBackPressed()
    }

    override fun onContactSelected(umkm: LaporanUmkm?) {
        val intent = Intent(this, DetailLaporan::class.java)
        intent.putExtra("id", umkm!!.kodeUmkm )
        startActivity(intent)
    }
}