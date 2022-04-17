package com.ardeveloper.plut.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.GridLayoutManager
import com.ardeveloper.plut.BaseActivity
import com.ardeveloper.plut.R
import com.ardeveloper.plut.adapter.AllProductAdapter
import com.ardeveloper.plut.adapter.PosProductAdapter
import com.ardeveloper.plut.data.db.Product
import com.ardeveloper.plut.databinding.ActivityAllProdukBinding
import com.ardeveloper.plut.databinding.ActivityKasirBinding
import com.google.zxing.integration.android.IntentIntegrator
import com.infield.epcs.utils.Status

class AllProduk : BaseActivity(), AllProductAdapter.ProductAdapterListener {

    private lateinit var b : ActivityAllProdukBinding
    private lateinit var adapter : AllProductAdapter
    private lateinit var productList : MutableList<Product>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        b = ActivityAllProdukBinding.inflate(layoutInflater)
        val view = b.root
        setContentView(view)
        initView()
        getData()
    }

    override fun onResume() {
        super.onResume()
        getData()
    }

    private fun getData() {
        setupViewModel()
        showLoadingDialog()
        viewModel.getProduk().observe(this, Observer {
            it?.let { resource ->
                when (resource.status) {
                    Status.SUCCESS -> {
                        productList.clear()
                        productList.addAll(resource.data!!)
                        adapter.notifyDataSetChanged()
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
        setSupportActionBar(b.toolbar9)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true);
        supportActionBar!!.setDisplayShowHomeEnabled(true);

        productList = ArrayList()
        adapter = AllProductAdapter(this,productList,this)

        val gridLayoutManager = GridLayoutManager(applicationContext, 2)
        b.recycler.layoutManager = gridLayoutManager // set LayoutManager to RecyclerView
        b.recycler.setHasFixedSize(true)
        b.recycler.itemAnimator = DefaultItemAnimator()
        b.recycler.adapter =  adapter

        b.imgScanner.setOnClickListener {
            val ii = IntentIntegrator(this).setCaptureActivity(
                ScannerActivity::class.java
            )
            ii.setOrientationLocked(false);
            ii.initiateScan()
        }
        b.etxtSearch.addTextChangedListener(
            object : TextWatcher {
                override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

                }

                override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                    adapter.filter.filter(p0)

                }

                override fun afterTextChanged(p0: Editable?) {
                    adapter.filter.filter(p0)

                }

            }
        )
    }

    override fun onItemSelected(product: Product?) {
        val intent = Intent(this, ProductDetail::class.java)
        intent.putExtra("produk", product!!.kodeProduk)
        startActivity(intent)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        val result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data)
        if (result.contents == null) {
            return
        }else{
            var dataQr = result.contents
            //dataQr = dataQr.replace("\\s+".toRegex(), "")
            //dataQr = dataQr.substring(0,12)
            Log.d("tessqr",dataQr)
            val intent = Intent(this, ProductDetail::class.java)
            intent.putExtra("produk", dataQr)
            startActivity(intent)
        }
    }
}