package com.ardeveloper.plut.view

import ApiService
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.widget.EditText
import android.widget.TextView
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ardeveloper.plut.BaseActivity
import com.ardeveloper.plut.adapter.PosProductAdapter
import com.ardeveloper.plut.api.ApiClient
import com.ardeveloper.plut.data.response.ResponseShop
import com.ardeveloper.plut.data.response.ResponseProduk
import com.ardeveloper.plut.databinding.ActivityKasirBinding
import com.ardeveloper.plut.preferences.SharedPrefs
import es.dmoral.toasty.Toasty
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class Kasir : BaseActivity(), PosProductAdapter.ProductAdapterListener,
    PosProductAdapter.getDataListener,PosProductAdapter.refreshListener{

    private lateinit var apiInterface: ApiService
    private val recyclerView: RecyclerView? = null
    var productAdapter: PosProductAdapter? = null
    var responseShop : ResponseShop? =null
    var txtNoProducts: TextView? = null
    private lateinit var b : ActivityKasirBinding
    var userId = ""
    private var badge : Int =0

    private lateinit var productList : MutableList<ResponseProduk>
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
        userId = SharedPrefs.getString(this,SharedPrefs.USERID)
        apiInterface = ApiClient.getClient(this).create(ApiService::class.java)
        productList = ArrayList()
        productAdapter = PosProductAdapter(this,productList,this,this,this)

        b.backImg.setOnClickListener {
            onBackPressed()
        }
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

        b.imageView13.setOnClickListener {
            val intent = Intent(this, TransaksiView::class.java)
            startActivity(intent)
        }
    }

    override fun onResume() {
        super.onResume()
        getData()
    }

    private fun getData() {
   //     setupViewModel()
        showLoadingDialog()
        badge=0
        apiInterface.getProdukShop(userId) .enqueue(object : Callback<ResponseShop> {
            override fun onResponse(
                call: Call<ResponseShop>,
                response: Response<ResponseShop>
            ) {
                if (response.isSuccessful){
                    productList.clear()
                        responseShop = response.body()
                        b.badge.setText(""+responseShop!!.total)
                        productList.addAll(responseShop!!.produk)
                        productAdapter!!.notifyDataSetChanged()
                        hideLoadingDialog()
                }else{
                    Toasty.error(this@Kasir,"Error : "+response.code()).show()
                    hideLoadingDialog()
                }
            }

            override fun onFailure(call: Call<ResponseShop>, t: Throwable) {
                Toasty.error(this@Kasir,"Error : "+t.message).show()
                hideLoadingDialog()
            }

        })
//        viewModel.getProduk().observe(this, Observer {
//            it?.let { resource ->
//                when (resource.status) {
//                    Status.SUCCESS -> {
//                        productList.clear()
//                        productList.addAll(resource.data!!)
//                        productAdapter!!.notifyDataSetChanged()
//                        hideLoadingDialog()
//                    }
//                    Status.ERROR -> {
//                        Log.d("tesDownload","Error"+it.message)
//                        hideLoadingDialog()
//                    }
//                    Status.LOADING -> {
//                        Log.d("tesDownload",resource.toString())
//                    }
//                }
//            }
//        })
    }

    override fun onItemSelected(product: ResponseProduk?) {

    }

    override fun onCartClick(kode : String,jumlah : Int) {
        showLoadingDialog()
        apiInterface.addCart(kode,userId.toString().toInt(),jumlah).enqueue(
            object :Callback<ResponseBody>{
                override fun onResponse(
                    call: Call<ResponseBody>,
                    response: Response<ResponseBody>
                ) {

                        Log.d("response",response.body()!!.string())
                    getData()
//                    hideLoadingDialog()
                }

                override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                    hideLoadingDialog()
                }

            }
        )

    }

    override fun refreshData() {
        getData()
    }

//    override fun addBadge(type: Int) {
//        Log.d("badge",""+type+" "+badge)
//        if (type==1){
//
//            badge++
//            Log.d("badge",""+type+" "+badge)
//        }
//        b.badge.setText(""+badge)
//        if (badge==0){
//            b.badge.visibility= View.GONE
//        }else{
//            b.badge.visibility= View.VISIBLE
//        }
//
//    }
}