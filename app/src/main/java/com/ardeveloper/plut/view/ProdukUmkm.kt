package com.ardeveloper.plut.view

import android.app.Activity
import android.app.SearchManager
import android.content.Context
import android.content.Intent
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
import com.ardeveloper.plut.adapter.ProductUmkmAdapter
import com.ardeveloper.plut.data.db.DaoMaster
import com.ardeveloper.plut.data.db.Product
import com.ardeveloper.plut.data.db.UMKM
import com.ardeveloper.plut.data.service.AllDbService
import com.ardeveloper.plut.databinding.ActivityProdukUmkmBinding
import com.infield.epcs.utils.Status
import com.obsez.android.lib.filechooser.ChooserDialog
import es.dmoral.toasty.Toasty
import java.io.File
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class ProdukUmkm : BaseActivity(), ProductUmkmAdapter.ItemAdapterListener {

    private var searchView: SearchView? = null
    private lateinit var b : ActivityProdukUmkmBinding
    private lateinit var productList : MutableList<Product>
    private lateinit var mAdapter : ProductUmkmAdapter
    private lateinit var umkm: UMKM
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        b = ActivityProdukUmkmBinding.inflate(layoutInflater)
        val view = b.root
        setContentView(view)
        initView()
        getData()

    }

    private fun getData() {
        showLoadingDialog()
        viewModel.getProduk(umkm.kodeUmkm).observe(this, Observer {
            it?.let { resource ->
                when (resource.status) {
                    Status.SUCCESS -> {
                        productList.clear()
                        productList.addAll(resource.data!!)
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

        val intent = intent
         umkm = (intent.getSerializableExtra("umkm") as? UMKM)!!
        val nama = umkm?.nama

        setSupportActionBar(b.toolbar7)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.setDisplayShowHomeEnabled(true)
        supportActionBar!!.title = "List Produk"
        supportActionBar!!.subtitle = nama
        productList = ArrayList()
        mAdapter = ProductUmkmAdapter(this,productList,this,umkm)

        val mLayoutManager: RecyclerView.LayoutManager = LinearLayoutManager(applicationContext)
        b.rvProductUmkm.layoutManager = mLayoutManager
        b.rvProductUmkm.itemAnimator = DefaultItemAnimator()
        b.rvProductUmkm.adapter = mAdapter

        b.fabAdd2.setOnClickListener {
            val intent = Intent(this, InputBarang::class.java)

            intent.putExtra("umkm",umkm)

            startActivity(intent)

        }
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
            folderChooser()
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

    override fun onItemSelected(product: Product?) {
        val intent = Intent(this, ProductDetail::class.java)
        intent.putExtra("produk", product!!.kodeProduk)
        startActivity(intent)

    }

    open fun folderChooser() {

        val folder = File(Environment.getExternalStorageDirectory(), "Plut/Export/Produk/"+umkm.kodeUmkm)
        if (!folder.exists()) {
            folder.mkdirs()
        }
        val dbService : AllDbService
        dbService = AllDbService(this)
        dbService.addToProduct(productList)

        onExport(folder.path)
//        ChooserDialog(this)
//            .displayPath(true)
//            .withFilter(true, false) // to handle the result(s)
//            .withChosenListener { path, pathFile ->
//                onExport(path)
//                Log.d("path", path)
//            }
//            .build()
//            .show()
    }

     fun onExport(path: String) {
//        val file = File(Environment.getExternalStorageDirectory(), "Plut/Export/Produk/"+umkm.kodeUmkm)
         val file = File(path)
        val format = SimpleDateFormat(" yyyy-MM-dd  HH.mm.ss")


        if (!file.exists()) {
            file.mkdirs()
        }
        // Export SQLite DB as EXCEL FILE
        Log.d("tes",file.path)
        val sqliteToExcel =
            SQLiteToExcel(applicationContext,"plut_db", path)
        sqliteToExcel.exportSingleTable("product", umkm.nama+" ${format.format(Date())}.xls", object : SQLiteToExcel.ExportListener {
            override fun onStart() {
              showLoadingDialog()
            }

            override fun onCompleted(filePath: String?) {
                val mHand = Handler()
                mHand.postDelayed({
                    hideLoadingDialog()
                    Toasty.success(
                        this@ProdukUmkm,
                       "Data Berhasil di Export",
                        Toast.LENGTH_SHORT
                    ).show()
                }, 5000)
            }

            override fun onError(e: Exception?) {
               hideLoadingDialog()
                Toasty.error(this@ProdukUmkm, "Gagal : "+ e!!.message, Toast.LENGTH_SHORT)
                    .show()
            }
        })
    }
}