package com.ardeveloper.plut.view

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.widget.EditText
import android.widget.TextView
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ardeveloper.plut.BaseActivity
import com.ardeveloper.plut.adapter.PosProductAdapter
import com.ardeveloper.plut.data.db.Product
import com.ardeveloper.plut.databinding.ActivityKasirBinding
import com.infield.epcs.utils.Status

class Kasir : BaseActivity(), PosProductAdapter.ProductAdapterListener {

    private val recyclerView: RecyclerView? = null
    var productAdapter: PosProductAdapter? = null
    var txtNoProducts: TextView? = null
    private lateinit var b : ActivityKasirBinding

    private lateinit var productList : MutableList<Product>
    var etxtSearch: EditText? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        b = ActivityKasirBinding.inflate(layoutInflater)
        val view = b.root
        setContentView(view)
        initView()
        getData()
    }

    private fun initView() {
        productList = ArrayList()
        productAdapter = PosProductAdapter(this,productList,this)

//        val mLayoutManager: RecyclerView.LayoutManager = LinearLayoutManager(applicationContext)
//        b.recycler.layoutManager = mLayoutManager

        // set a GridLayoutManager with default vertical orientation and 3 number of columns
        val gridLayoutManager = GridLayoutManager(applicationContext, 2)
        b.recycler.layoutManager = gridLayoutManager // set LayoutManager to RecyclerView
        b.recycler.setHasFixedSize(true)
        b.recycler.itemAnimator = DefaultItemAnimator()
        b.recycler.adapter = productAdapter

        b.etxtSearch.addTextChangedListener(
            object : TextWatcher{
                override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

                }

                override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                    productAdapter!!.filter.filter(p0)

                }

                override fun afterTextChanged(p0: Editable?) {
                    productAdapter!!.filter.filter(p0)

                }

            }
        )
    }

    private fun getData() {
        setupViewModel()
        viewModel.getProduk().observe(this, Observer {
            it?.let { resource ->
                when (resource.status) {
                    Status.SUCCESS -> {
                        productList.clear()
                        productList.addAll(resource.data!!)
                        productAdapter!!.notifyDataSetChanged()

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

    override fun onItemSelected(product: Product?) {

    }
}