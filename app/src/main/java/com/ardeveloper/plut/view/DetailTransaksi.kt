package com.ardeveloper.plut.view

import ApiService
import android.annotation.SuppressLint
import android.bluetooth.BluetoothAdapter
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ardeveloper.plut.BaseActivity
import com.ardeveloper.plut.R
import com.ardeveloper.plut.adapter.ConfirmationAdapter
import com.ardeveloper.plut.adapter.DetailAdapter
import com.ardeveloper.plut.api.ApiClient
import com.ardeveloper.plut.data.response.ResponseCart
import com.ardeveloper.plut.data.response.ResponseTransaksi
import com.ardeveloper.plut.data.response.Transaksi
import com.ardeveloper.plut.data.response.TransaksiItem
import com.ardeveloper.plut.databinding.ActivityDetailTransaksiBinding
import com.ardeveloper.plut.databinding.ActivitySuksesBinding
import com.ardeveloper.plut.utils.Gblvariabel
import com.ardeveloper.plut.utils.ImageHelper
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.SimpleDateFormat

class DetailTransaksi : BaseActivity() {
    private lateinit var apiInterface: ApiService
    private lateinit var b : ActivityDetailTransaksiBinding
    private var idtrans = ""
    private lateinit var transaksiResponse: ResponseTransaksi
    private lateinit var transaksi: Transaksi
    private lateinit var item: MutableList<TransaksiItem>

    var adapter : DetailAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        b= ActivityDetailTransaksiBinding.inflate(layoutInflater)
        val view = b.root
        setContentView(view)
        initView()
        getData()
    }

    private fun getData() {
        showLoadingDialog()

//        Log.d("gbl",Gblvariabel.idback)
        if (idtrans == ""){
            idtrans=Gblvariabel.idback
        }
        apiInterface.getTransksi(idtrans).enqueue(object : Callback<ResponseTransaksi> {
            override fun onResponse(
                call: Call<ResponseTransaksi>,
                response: Response<ResponseTransaksi>
            ) {
                if (response.isSuccessful){
                    transaksiResponse = response.body()!!
                    transaksi = transaksiResponse.transaksi
                    item = transaksiResponse.item

                    setData()

                }
                hideLoadingDialog()
            }

            override fun onFailure(call: Call<ResponseTransaksi>, t: Throwable) {
                hideLoadingDialog()
            }

        })
    }

    private fun setData() {
        b.tvTotalBig.text = "Rp. "+convertToCurrency(transaksi.totalHarga.toString())
        b.tvId.text = "ID Transaksi : "+transaksi.idTransaksi
        b.tvDate.text = transaksi.createdAt
        b.tvPayment.text = transaksi.metode
        b.tvOperator.text = transaksi.username
        b.tvStatus.text = "Lunas"
        b.tvSubtotal.text = "Rp. "+convertToCurrency(transaksi.subtotal.toString())
        b.tvDiscount.text = "Rp. "+convertToCurrency(transaksi.diskon.toString())
        b.tvTotal.text = "Rp. "+convertToCurrency(transaksi.totalHarga.toString())
        b.tvBayar.text = "Rp. "+convertToCurrency(transaksi.uangDiterima.toString())
        b.tvKembalian.text = "Rp. "+convertToCurrency(transaksi.kembalian.toString())

        adapter!!.clearAdapter()
        adapter!!.setItems(item)
        adapter!!.notifyDataSetChanged()
    }

    private fun initView() {
        val intent  = intent
        idtrans = intent.getStringExtra("id")!!
        Log.d("tes",idtrans)
      //  transid = intent.getStringExtra("idtrans")!!
        apiInterface = ApiClient.getClient(this).create(ApiService::class.java)
        setSupportActionBar(b.toolbar14)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true);
        supportActionBar!!.setDisplayShowHomeEnabled(true);
        supportActionBar!!.title = "Detail Transaksi"

        adapter = DetailAdapter()
        val mLayoutManager: RecyclerView.LayoutManager = LinearLayoutManager(applicationContext)
        b.rvList.layoutManager = mLayoutManager // set LayoutManager to rvCartView
        b.rvList.setHasFixedSize(true)
        b.rvList.itemAnimator = DefaultItemAnimator()
        b.rvList.adapter = adapter

        b.btnShare.setOnClickListener {
            ImageHelper.takeScreenshot(this,b.nsScroll)
        }

        b.btnPrinter.setOnClickListener {
            cetak()
        }

    }

    override fun onBackPressed() {

        if (Gblvariabel.from.equals("1")){
            Gblvariabel.from = ""
            val intent = Intent(this, MainActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
            startActivity(intent)
            finish()
            super.onBackPressed()
        }else{
            super.onBackPressed()
        }

    }

    @SuppressLint("MissingPermission")
    private fun cetak() {
        val mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter()
        mBluetoothAdapter.enable()
        pilihan = 2

        val bar = transaksi.idTransaksi

        val format = SimpleDateFormat(" yyyy-MM-dd  HH:mm:ss")
        var ds = ""
        for (i in 0 until item.size){
            val harga = item[i].totalHarga/item[i].totalProduk
            ds+= "[L]<b>${item[i].nama}</b>"+
                 "[L]<font size='8'>${item[i].totalProduk}x@${harga}</font>[R]<font size='8'>${item[i].totalHarga}</font>"+
                 "[L]"
        }
        dataCetak =   """
            [L]
            [C]<b><font size='12'>GALERI PLUT KUMKM</font></b>
            [C]<b><font size='10'>Dinas Koperasi & UKM SUMUT</font></b>
            [L]
            [C]Rp.${transaksi.totalHarga}
            [C]<u><font size='10'>${transaksi.idTransaksi}</font></u>
            [L]
            [L]Tanggal : [R]${transaksi.createdAt}
            [L]Metode Pembayaran : [R]${transaksi.metode}
            [L]Operator : [R]${transaksi.username}
            [L]Status: [R]Lunas
            [C]================================
            [L]<b>List Produk</b>
            [C]================================
            $ds
            [C]--------------------------------
            [L]Subtotal :Rp.[R]${transaksi.subtotal}
            [L]Diskon :[R]${transaksi.diskon}
            [C]--------------------------------
            [L]Total :[R]${transaksi.totalHarga}
            [L]Pembayaran :[R]${transaksi.uangDiterima}
            [L]Kembalian :[R]${transaksi.kembalian}
            [C]================================
            [C]<font size='8'>Terima Kasih</font>
            [C]<font size='8'>atas kunjungan anda</font>
            [C]<font size='8'>#dukungumkm</font>
            [C]<font size='8'>#banggabuatanindonesia</font>
            """

        Log.d("datacetak",dataCetak)
        if(selectedDevice==null){

            browseBluetoothDevice(2)
        }else{
            printBluetooth(2)
        }

    }
}