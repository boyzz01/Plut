package com.ardeveloper.plut.view

import ApiService
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ardeveloper.plut.BaseActivity
import com.ardeveloper.plut.R
import com.ardeveloper.plut.adapter.KeranjangAdapter
import com.ardeveloper.plut.api.ApiClient
import com.ardeveloper.plut.data.response.ResponseCart
import com.ardeveloper.plut.data.response.ResponseProduk
import com.ardeveloper.plut.databinding.ActivityTransaksiBinding
import com.ardeveloper.plut.preferences.SharedPrefs
import es.dmoral.toasty.Toasty
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class TransaksiView : BaseActivity(), KeranjangAdapter.cartListener {

    private lateinit var apiInterface: ApiService
    private lateinit var productList : MutableList<ResponseProduk>
    var cartAdapter : KeranjangAdapter? = null
    var userId = ""
    var responseCart : ResponseCart? = null
    private lateinit var b : ActivityTransaksiBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_transaksi)
        b = ActivityTransaksiBinding.inflate(layoutInflater)
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
        showLoadingDialog()

        apiInterface.getCart(userId).enqueue(object : Callback<ResponseCart> {
            override fun onResponse(
                call: Call<ResponseCart>,
                response: Response<ResponseCart>
            ) {
                if (response.isSuccessful){
                    productList.clear()
                    responseCart = response.body()


                    if (responseCart?.totalHarga!=null){
                        b.viewRefresh.visibility = View.GONE
                        b.vTotal.visibility = View.VISIBLE
                        b.tvTotal.text = "Rp."+ convertToCurrency(responseCart!!.totalHarga)
                        b.tvNumber.text = ""+ convertToCurrency(responseCart!!.totalItem)
                        productList.addAll(responseCart!!.produk)
                        cartAdapter!!.notifyDataSetChanged()
                    }else{
                        b.vTotal.visibility = View.GONE
                        Toasty.info(this@TransaksiView,"No Data").show()
                        b.viewRefresh.visibility = View.VISIBLE
                    }


                    hideLoadingDialog()

                }else{
                    Toasty.error(this@TransaksiView,"Error : "+response.code()).show()
                    hideLoadingDialog()
                }
            }

            override fun onFailure(call: Call<ResponseCart>, t: Throwable) {
                Toasty.error(this@TransaksiView,"Error : "+t.message).show()
                hideLoadingDialog()
            }
        })
    }

    private fun initView() {
        userId = SharedPrefs.getString(this, SharedPrefs.USERID)
        apiInterface = ApiClient.getClient(this).create(ApiService::class.java)
        productList = ArrayList()
        cartAdapter = KeranjangAdapter(this,productList,this)

        b.imageView13.setOnClickListener {
            onBackPressed()
        }
        b.backImg.setOnClickListener {
            onBackPressed()
        }

        b.imgRefresh.setOnClickListener {
            getData()
        }

        b.bBayar.setOnClickListener {
            val intent = Intent(this,Konfirmasi::class.java)
            startActivity(intent)
        }
        val mLayoutManager: RecyclerView.LayoutManager = LinearLayoutManager(applicationContext)
        b.rvCart.layoutManager = mLayoutManager // set LayoutManager to rvCartView
        b.rvCart.setHasFixedSize(true)
        b.rvCart.itemAnimator = DefaultItemAnimator()
        b.rvCart.adapter = cartAdapter

    }

//    override fun addTotal(jumlah: Int, harga: Int) {
//        totalBarang+=jumlah
//        totalUang+=(jumlah*harga)
//
//        b.tvTotal.setText("Rp."+ convertToCurrency(totalUang.toString()))
//        b.tvNumber.setText(""+ convertToCurrency(jumlah.toString()))
//    }

    override fun onCartClick(kode: String, jumlah: Int) {
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
}