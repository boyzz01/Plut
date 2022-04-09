package com.ardeveloper.plut.view

import android.app.SearchManager
import android.content.Context
import android.content.Intent
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
import com.ardeveloper.plut.adapter.UmkmAdapter
import com.ardeveloper.plut.data.db.UMKM
import com.ardeveloper.plut.databinding.ActivityUmkmBinding

import com.infield.epcs.utils.Status


class Umkm : BaseActivity(),UmkmAdapter.UmkmAdapterListener{

    private var searchView: SearchView? = null
    private lateinit var b : ActivityUmkmBinding
    private lateinit var umkmList : MutableList<UMKM>
    private lateinit var mAdapter : UmkmAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        b = ActivityUmkmBinding.inflate(layoutInflater)
        val view = b.root
        setContentView(view)
        init()
        getData()
    }

    private fun getData() {
        setupViewModel()
        viewModel.getUmkm().observe(this, Observer {
            it?.let { resource ->
                when (resource.status) {
                    Status.SUCCESS -> {
                        umkmList.clear()
                        umkmList.addAll(resource.data!!)
                        mAdapter.notifyDataSetChanged()

                    }
                    Status.ERROR -> {
                        Log.d("tesDownload","Error"+it.message)

                    }
                    Status.LOADING -> {
                        Log.d("tesDownload",resource.toString())
                    }
                }
            }
        })

    }

    private fun init() {
        setSupportActionBar(b.toolbar6)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true);
        supportActionBar!!.setDisplayShowHomeEnabled(true);

        umkmList = ArrayList()
        mAdapter = UmkmAdapter(this,umkmList,this)
        val mLayoutManager: RecyclerView.LayoutManager = LinearLayoutManager(applicationContext)
        b.rvUmkm.layoutManager = mLayoutManager
        b.rvUmkm.itemAnimator = DefaultItemAnimator()
        b.rvUmkm.adapter = mAdapter
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
        return if (id == R.id.action_search) {
            true
        } else super.onOptionsItemSelected(item)

    }

    override fun onBackPressed() {
        // close search view on back button pressed
        if (!searchView!!.isIconified()) {
            searchView!!.isIconified = true
            return
        }
        super.onBackPressed()
    }

    override fun onContactSelected(umkm: UMKM?) {
        val intent = Intent(this@Umkm, InputBarang::class.java)
        intent.putExtra("nama",umkm!!.nama)
        intent.putExtra("id", umkm.id)
        intent.putExtra("kota", umkm.kode_kota)
        intent.putExtra("nib", umkm.nib)
        startActivity(intent)
    }


}