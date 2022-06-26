package com.ardeveloper.plut.view

import ApiService
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.widget.ArrayAdapter
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ardeveloper.plut.BaseActivity
import com.ardeveloper.plut.R
import com.ardeveloper.plut.adapter.ConfirmationAdapter
import com.ardeveloper.plut.api.ApiClient
import com.ardeveloper.plut.data.response.ResponseCart
import com.ardeveloper.plut.data.response.ResponseProduk
import com.ardeveloper.plut.databinding.ActivityKonfirmasiBinding
import com.ardeveloper.plut.preferences.SharedPrefs
import com.ardeveloper.plut.utils.Helper
import es.dmoral.toasty.Toasty
import okhttp3.ResponseBody
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class Konfirmasi : BaseActivity() {

    private lateinit var apiInterface: ApiService
    private lateinit var productList : MutableList<ResponseProduk>
    var cartAdapter : ConfirmationAdapter? = null
    var userId = ""
    var responseCart : ResponseCart? = null
    var kembalian = 0
    var subtotal = 0
    var bayar = 0
    var total = 0
    var diskon = 0
    var bank = ""
    var nokartu = ""
    var pakaiBank :Boolean= false
    var metode=""
    //0 = Rp 1=%

    var typediskon = 0

    private lateinit var b : ActivityKonfirmasiBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        b = ActivityKonfirmasiBinding.inflate(layoutInflater)
        val view = b.root
        setContentView(view)
        initView()
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
                    cartAdapter!!.clearAdapter()
                    responseCart = response.body()

                    subtotal = responseCart!!.totalHarga.toInt()
                    total = subtotal-diskon
                    kembalian = (bayar) -(total)

                    if(kembalian>=0){
                        b.tvKembalian.text = "Kembali : Rp."+convertToCurrency(kembalian.toString())
                        b.tvKembalian.setTextColor(ContextCompat.getColor(this@Konfirmasi, R.color.colorAccent))
                    }else{
                        b.tvKembalian.text = "Kekurangan pembayaran : Rp."+convertToCurrency(kembalian.toString())
                        b.tvKembalian.setTextColor(ContextCompat.getColor(this@Konfirmasi,R.color.vermillion))
                    }
                   b.tvTotal.text = "Rp."+ Helper.convertToCurrency(responseCart!!.totalHarga)
                    b.tvSubtotal.text = "Rp."+ Helper.convertToCurrency(responseCart!!.totalHarga)

//                    b.tvNumber.text = ""+ Helper.convertToCurrency(responseCart!!.totalItem)

                    productList.addAll(responseCart!!.produk)
                    cartAdapter!!.setItems(productList)
                    cartAdapter!!.notifyDataSetChanged()

                    hideLoadingDialog()

                }else{
                    Toasty.error(this@Konfirmasi,"Error : "+response.code()).show()
                    hideLoadingDialog()
                }
            }

            override fun onFailure(call: Call<ResponseCart>, t: Throwable) {
                Toasty.error(this@Konfirmasi,"Error : "+t.message).show()
                hideLoadingDialog()
            }
        })
    }

    private fun initView() {
        setSupportActionBar(b.toolbar13)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true);
        supportActionBar!!.setDisplayShowHomeEnabled(true);
        supportActionBar!!.title = "Konfirmasi Pesanan"

        userId = SharedPrefs.getString(this, SharedPrefs.USERID)
        apiInterface = ApiClient.getClient(this).create(ApiService::class.java)
        productList = ArrayList()
        cartAdapter = ConfirmationAdapter()

        val mLayoutManager: RecyclerView.LayoutManager = LinearLayoutManager(applicationContext)
        b.rvList.layoutManager = mLayoutManager // set LayoutManager to rvCartView
        b.rvList.setHasFixedSize(true)
        b.rvList.itemAnimator = DefaultItemAnimator()
        b.rvList.adapter = cartAdapter

        val ket = arrayOf("BNI","BCA","BRI","BTN","MANDIRI","LAINNYA")
        val adapter = ArrayAdapter(this, R.layout.dropdown_item, ket)
        b.spnBank.setAdapter(adapter)

        b.rbTunai.isChecked = true

        b.rbRupiah.isChecked = true
        typediskon = 0
        metode ="Tunai"
        hideBank()

        b.rgDiskon.setOnCheckedChangeListener(RadioGroup.OnCheckedChangeListener { radioGroup, i ->
            run {
                when (i) {
                    R.id.rb_rupiah -> {
                        // do something when radio button 1 is selected
                       typediskon = 0
                        b.etDiscount.hint = "Rp"

                        if (b.etDiscount.text.toString()==""){
                            checkDiskon(0)
                        }else{
                            checkDiskon(b.etDiscount.text.toString().toInt())
                        }

                    }
                    R.id.rb_persen -> {
                      typediskon = 1
                        b.etDiscount.hint = "%"
                        if (b.etDiscount.text.toString()==""){
                            checkDiskon(0)
                        }else{
                            checkDiskon(b.etDiscount.text.toString().toInt())
                        }
                    }

                    // add more cases here to handle other buttons in the your RadioGroup
                }
            }
        })
        b.rgPayment.setOnCheckedChangeListener(RadioGroup.OnCheckedChangeListener { group, checkedId -> // checkedId is the RadioButton selected

            val radio:RadioButton = group.findViewById(checkedId)
            metode = radio.text.toString()
            Log.d("tes",metode)

            run {
                when (checkedId) {
                    R.id.rb_tunai -> {
                        // do something when radio button 1 is selected
                        hideBank()
                    }
                    R.id.rb_nontunai -> {
                        showBank()
                    }
                    R.id.rb_piutang -> {
                        showBank()
                    }
                    R.id.ewallet -> {
                        hideBank()
                    }
                    R.id.qris -> {
                        hideBank()
                    }
                    // add more cases here to handle other buttons in the your RadioGroup
                }
            }


        })

        b.bBayar.setOnClickListener {
//            Log.d("data1",""+bayar)
//            Log.d("data2",""+total)
//            Log.d("data3",""+subtotal)
//            Log.d("data4",""+kembalian)

            if (pakaiBank){
                nokartu = b.etNomorKartu.text.toString()
                bank = b.spnBank.text.toString()
                if (bank == ""){
                    Toasty.error(this,"Mohon Pilih Bank").show()
                    return@setOnClickListener
                }
                if (nokartu == ""){
                    Toasty.error(this,"No Kartu Tidak Boleh Kosong").show()
                    return@setOnClickListener
                }

            }

            Log.d("data",metode+" "+bank+" "+nokartu)
            if (diskon>subtotal){
                Toasty.error(this,"Diskon tidak boleh lebih dari subtotal").show()
                return@setOnClickListener
            }
            if (kembalian<0){
                Toasty.error(this,"Mohon Bayar dengan uang yang pas").show()
            }else{
                val alert= AlertDialog.Builder(this)
                alert.setTitle("Konfirmasi");
                alert.setMessage("Transaksi Sudah Sesuai?");
                alert.setPositiveButton(
                    "Ya"
                ) { dialog, which ->
                    uploadData()
                    dialog.dismiss()
                }

                alert.setNegativeButton(
                    "Tidak"
                ) { dialog, which -> dialog.dismiss() }

                alert.show()

            }
        }
        b.etPay.addTextChangedListener(object : TextWatcher{
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {



            }

            override fun afterTextChanged(p0: Editable?) {
                Handler().postDelayed({
                if(b.etPay.hasFocus()) {
                    if (p0.toString().isNotEmpty()) {
                        bayar = p0.toString().toInt()
                        kembalian = (bayar) - (total)

                        if (kembalian >= 0) {
                            b.tvKembalian.text =
                                "Kembali : Rp." + convertToCurrency(kembalian.toString())
                            b.tvKembalian.setTextColor(
                                ContextCompat.getColor(
                                    this@Konfirmasi,
                                    R.color.colorAccent
                                )
                            )
                        } else {
                            b.tvKembalian.text =
                                "Kekurangan pembayaran : Rp." + convertToCurrency(kembalian.toString())
                            b.tvKembalian.setTextColor(
                                ContextCompat.getColor(
                                    this@Konfirmasi,
                                    R.color.vermillion
                                )
                            )
                        }
                    } else {
                        b.etPay.setText("0")
                    }
                }
                }, 200) // 3000 is the delayed time in milliseconds.

            }

        })

        b.etDiscount.addTextChangedListener(object : TextWatcher{
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun afterTextChanged(p0: Editable?) {
                Handler().postDelayed({
                    if(b.etDiscount.hasFocus()) {
                if (p0.toString().isNotEmpty()) {

                    checkDiskon(p0.toString().toInt())

                }else{
                    b.etDiscount.setText("0")
                }

                    }
                }, 200) // 3000 is the delayed time in milliseconds.



            }

        })


    }

    private fun checkDiskon(jumlah: Int) {


        if (typediskon==0){
            diskon = jumlah
            b.tvDiscount.text = "Rp." + convertToCurrency(jumlah.toString())
            total = subtotal - diskon
            b.tvTotal.text = "Rp." + Helper.convertToCurrency(total.toString())

            kembalian = (bayar) - (total)

            if (kembalian >= 0) {
                b.tvKembalian.text =
                    "Kembali : Rp." + convertToCurrency(kembalian.toString())
                b.tvKembalian.setTextColor(
                    ContextCompat.getColor(
                        this@Konfirmasi,
                        R.color.colorAccent
                    )
                )
            } else {
                b.tvKembalian.text =
                    "Kekurangan pembayaran : Rp." + convertToCurrency(kembalian.toString())
                b.tvKembalian.setTextColor(
                    ContextCompat.getColor(
                        this@Konfirmasi,
                        R.color.vermillion
                    )
                )
            }
        }else{
            val temp = subtotal*jumlah/100
            diskon = temp
            b.tvDiscount.text = "Rp." + convertToCurrency(diskon.toString())
            total = subtotal - diskon
            b.tvTotal.text = "Rp." + Helper.convertToCurrency(total.toString())

            kembalian = (bayar) - (total)

            if (kembalian >= 0) {
                b.tvKembalian.text =
                    "Kembali : Rp." + convertToCurrency(kembalian.toString())
                b.tvKembalian.setTextColor(
                    ContextCompat.getColor(
                        this@Konfirmasi,
                        R.color.colorAccent
                    )
                )
            } else {
                b.tvKembalian.text =
                    "Kekurangan pembayaran : Rp." + convertToCurrency(kembalian.toString())
                b.tvKembalian.setTextColor(
                    ContextCompat.getColor(
                        this@Konfirmasi,
                        R.color.vermillion
                    )
                )
            }
        }

    }

    private fun showBank() {
        b.llNomorKartu.visibility = View.VISIBLE
        b.bankDebit.visibility = View.VISIBLE
        pakaiBank = true
    }

    private fun hideBank(){
        b.llNomorKartu.visibility = View.GONE
        b.bankDebit.visibility = View.GONE
        b.etNomorKartu.setText("")
        bank =""
        nokartu=""
        pakaiBank = false
    }

    private fun uploadData() {
        showLoadingDialog()
        apiInterface.addTransaksi(total,userId.toInt(),diskon,responseCart!!.totalItem.toString().toInt(),kembalian,subtotal,bayar,metode, bank, nokartu)
            .enqueue(object : Callback<ResponseBody> {
                override fun onResponse(
                    call: Call<ResponseBody>,
                    response: Response<ResponseBody>
                ) {

                    if (response.isSuccessful){
                        val jsonString : String= response.body()!!.string()
                        Log.d("json",jsonString)
                        val jsonResult = JSONObject(jsonString)
                        val sukses : Boolean = jsonResult.optBoolean("success")
                        val idtrans : String = jsonResult.getString("data")
                        if (sukses){
                            Log.d("tes",idtrans)
                            val intent = Intent(this@Konfirmasi, Sukses::class.java)
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
                            intent.putExtra("idtrans",idtrans)
                            startActivity(intent)
                            finish()
                        }else{
                            Toasty.error(this@Konfirmasi,"Error :"+idtrans)
                        }

                    }else{
                        Toasty.error(this@Konfirmasi,"Error : code"+response.code())
                    }
                  //  Log.d("response",response.body()!!.string())
                    hideLoadingDialog()
                }

                override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                    Log.d("response","error:"+t.message)
                    hideLoadingDialog()
                }

            })
    }
}