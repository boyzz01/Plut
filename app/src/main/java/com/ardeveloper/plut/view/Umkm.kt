package com.ardeveloper.plut.view

import ApiService
import android.app.SearchManager
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.SearchView
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ardeveloper.plut.BaseActivity
import com.ardeveloper.plut.R
import com.ardeveloper.plut.adapter.UmkmAdapter
import com.ardeveloper.plut.api.ApiClient
import com.ardeveloper.plut.data.db.UMKM
import com.ardeveloper.plut.data.response.TransaksiItem
import com.ardeveloper.plut.databinding.ActivityUmkmBinding

import com.infield.epcs.utils.Status
import es.dmoral.toasty.Toasty
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class Umkm : BaseActivity(),UmkmAdapter.UmkmAdapterListener, UmkmAdapter.deleteListener {

    private var searchView: SearchView? = null
    private lateinit var b : ActivityUmkmBinding
    private lateinit var umkmList : MutableList<UMKM>
    private lateinit var mAdapter : UmkmAdapter
    private lateinit var apiInterface: ApiService
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        b = ActivityUmkmBinding.inflate(layoutInflater)
        val view = b.root
        setContentView(view)
        init()

        getData()
    }

    override fun onResume() {
        super.onResume()
        getData()
    }
    private fun getData() {
        showLoadingDialog()

        viewModel.getUmkm().observe(this, Observer {
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

    private fun init() {
        setSupportActionBar(b.toolbar6)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true);
        supportActionBar!!.setDisplayShowHomeEnabled(true);
        apiInterface = ApiClient.getClient(this).create(ApiService::class.java)
        umkmList = ArrayList()
        mAdapter = UmkmAdapter(this,umkmList,this,this)
        val mLayoutManager: RecyclerView.LayoutManager = LinearLayoutManager(applicationContext)
        b.rv.layoutManager = mLayoutManager
        b.rv.itemAnimator = DefaultItemAnimator()
        b.rv.adapter = mAdapter

        b.fabAdd.setOnClickListener {
            val intent = Intent(this, RegisterUmkm::class.java)
            startActivity(intent)
        }
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

    override fun onContactSelected(umkm: UMKM?) {
        val intent = Intent(this@Umkm, ProdukUmkm::class.java)
        intent.putExtra("umkm",umkm)
//        intent.putExtra("nama",umkm!!.nama)
//        intent.putExtra("id", umkm.kodeUmkm)
//        intent.putExtra("kota", umkm.kode_kota)
//        intent.putExtra("nib", umkm.nib)
        startActivity(intent)
    }

    override fun onDelete(umkm: UMKM?) {

        val alert= AlertDialog.Builder(this)
        alert.setTitle("Hapus UMKM?");
        alert.setMessage("Produk UMKM ini juga akan dihapus, Anda Yakin?");
        alert.setPositiveButton(
            "Ya"
        ) { dialog, which ->
            apiInterface.hapusUmkm(umkm!!.kodeUmkm).enqueue(object : Callback<ResponseBody>{
                override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                    if (response.isSuccessful){
                        Toasty.success(this@Umkm,"UMKM Berhasil Dihapus").show()
                        getData()
                    }else{
                        Toasty.error(this@Umkm,"Error "+response.code()).show()
                    }
                }

                override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                    Toasty.error(this@Umkm,"Error "+t.message).show()
                }

            })
            dialog.dismiss()
        }
        alert.setNegativeButton(
            "Tidak"
        ) { dialog, which -> dialog.dismiss() }

        alert.show()

    }

    private fun uploadData() {

    }


}