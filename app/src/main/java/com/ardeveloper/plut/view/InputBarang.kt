package com.ardeveloper.plut.view

import ApiService
import `in`.mayanknagwanshi.imagepicker.ImageSelectActivity
import android.content.Intent
import android.graphics.BitmapFactory
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.Observer
import com.ardeveloper.plut.BaseActivity
import com.ardeveloper.plut.R
import com.ardeveloper.plut.api.ApiClient
import com.ardeveloper.plut.api.RetrofitBuilder
import com.ardeveloper.plut.data.db.Kategori
import com.ardeveloper.plut.data.db.Product
import com.ardeveloper.plut.data.db.UMKM
import com.ardeveloper.plut.data.db.User
import com.ardeveloper.plut.databinding.ActivityInputBarangBinding
import com.ardeveloper.plut.databinding.ActivityUmkmBinding
import com.google.gson.Gson
import com.infield.epcs.utils.Status
import es.dmoral.toasty.Toasty
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.ResponseBody
import org.json.JSONObject
import retrofit2.Callback
import retrofit2.Call
import retrofit2.Response
import java.io.File

class InputBarang : BaseActivity() {
    
    private lateinit var b : ActivityInputBarangBinding
    private lateinit var kategoriList : MutableList<Kategori>
    private lateinit var adapterKategori : MutableList<String>
    private lateinit var umkm : UMKM
    var mediaPath: String? = "N/A"
    var encodedImage: String? = "N/A"
    var imageFile : File? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        b = ActivityInputBarangBinding.inflate(layoutInflater)
        val view = b.root
        setContentView(view)
        init()
        getData()

        b.button2.setOnClickListener {
            if (b.spnKategori.selectedItem==null){
                Toasty.error(this,"Mohon pilih kategori terlebih dahulu", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            if (b.nama.text!!.isEmpty()){
                Toasty.error(this,"Nama Produk tidak boleh kosong",Toast.LENGTH_SHORT).show()
                b.nama.requestFocus()
                return@setOnClickListener
            }
            if (b.harga.text!!.isEmpty()){
                Toasty.error(this,"Harga tidak boleh kosong",Toast.LENGTH_SHORT).show()
                b.harga.requestFocus()
                return@setOnClickListener
            }
            if (b.stock.text!!.isEmpty()){
                Toasty.error(this,"Stock tidak boleh kosong",Toast.LENGTH_SHORT).show()
                b.stock.requestFocus()
                return@setOnClickListener
            }
            if (imageFile==null){
                Toasty.error(this,"Mohon Pilih Foto",Toast.LENGTH_SHORT).show()
                b.txtChooseImage2.requestFocus()
                return@setOnClickListener
            }
            hideSoftKeyboard(this,it)
            val alert= AlertDialog.Builder(this)
            alert.setTitle("Konfirmasi");
            alert.setMessage("Apakah Data Sudah Benar?");
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

    private fun uploadData() {
        showLoadingDialog()
        val apiInterface = ApiClient.getClient(this).create(ApiService::class.java)

        val requestFile: RequestBody = RequestBody.create(
            "image/*".toMediaTypeOrNull(), imageFile!!
        )

        val nama: RequestBody = RequestBody.create(
            "text/plain".toMediaTypeOrNull(),
            b.nama.text.toString()
        )

        val harga: RequestBody = RequestBody.create(
            "text/plain".toMediaTypeOrNull(),
            b.harga.text.toString()
        )
        val kategori: RequestBody = RequestBody.create(
            "text/plain".toMediaTypeOrNull(),
            b.spnKategori.selectedItem.toString()
        )
        val kodeUmkm: RequestBody = RequestBody.create(
            "text/plain".toMediaTypeOrNull(),
            umkm.kodeUmkm
        )
        val kodeKota: RequestBody = RequestBody.create(
            "text/plain".toMediaTypeOrNull(),
            umkm.kode_kota
        )
        val stock: RequestBody = RequestBody.create(
            "text/plain".toMediaTypeOrNull(),
            b.stock.text.toString()
        )
        val foto = MultipartBody.Part.createFormData("foto", imageFile!!.name, requestFile)

        apiInterface.addProduk(nama,harga,kategori,kodeUmkm,kodeKota,foto,stock)
            .enqueue(object : Callback<ResponseBody> {
                override fun onResponse(
                    call: Call<ResponseBody>,
                    response: Response<ResponseBody>
                ) {
                    if (response.isSuccessful){
                        val jsonString : String= response.body()!!.string().toString()
                        val jsonResult = JSONObject(jsonString)
                        val sukses : Boolean = jsonResult.optBoolean("success")

                        if (sukses){
                            val gson = Gson()
                            val produk : Product = gson.fromJson(jsonResult.getJSONObject("data").toString(),
                                Product::class.java)
                            Toasty.success(this@InputBarang,"Produk Berhasil Ditambahkan").show()
                            resetAll()
                            val intent = Intent(this@InputBarang, ProductDetail::class.java)
                            intent.putExtra("produk", produk!!.kodeProduk)
                            startActivity(intent)

                        }else{
                            Toasty.error(this@InputBarang,"Gagal Menambah Produk").show()
                        }

                    }else{
                        Toasty.error(this@InputBarang,"Error : "+response.code()).show()
                    }
                    hideLoadingDialog()
                }

                override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                    Toasty.error(this@InputBarang,"Error : "+t.message).show()
                    hideLoadingDialog()
                }

            })

    }

    private fun resetAll() {
        b.spnKategori.setSelection(0)
        b.nama.setText("")
        b.harga.setText("")
        b.stock.setText("")
        imageFile = null
        b.imageUmkm2.setImageResource(R.drawable.image_placeholder)
    }

    private fun init(){
        val intent = intent
        umkm = (intent.getSerializableExtra("umkm") as? UMKM)!!
        val nama = umkm?.nama

        setSupportActionBar(b.toolbar3)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true);
        supportActionBar!!.setDisplayShowHomeEnabled(true);
        supportActionBar!!.title = "Tambah Produk"
        supportActionBar!!.subtitle = nama
        kategoriList = ArrayList()
        adapterKategori = ArrayList()
        val adapter = ArrayAdapter(this, R.layout.dropdown_item, adapterKategori)
        b.spnKategori.setTitle("Pilih Kategori Barang")
        b.spnKategori.setPositiveButton("Ok")
        b.spnKategori.adapter = adapter

        b.txtChooseImage2.setOnClickListener{
            val intent = Intent(this, ImageSelectActivity::class.java)
            intent.putExtra(ImageSelectActivity.FLAG_COMPRESS, true) //default is true
            intent.putExtra(ImageSelectActivity.FLAG_CAMERA, true) //default is true
            intent.putExtra(ImageSelectActivity.FLAG_GALLERY, true) //default is true

            startActivityForResult(intent, 1213)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
                super.onActivityResult(requestCode, resultCode, data)
        try {

            // When an Image is picked
            if (requestCode == 1213 && resultCode == RESULT_OK && null != data) {
                mediaPath = data.getStringExtra(ImageSelectActivity.RESULT_FILE_PATH)
                Log.d("disini",mediaPath.toString())
                val selectedImage = BitmapFactory.decodeFile(mediaPath)
                b.imageUmkm2.setImageBitmap(selectedImage)
                encodedImage = encodeImage(selectedImage)

                imageFile = File(mediaPath)
                Log.d("imagefile",""+ imageFile!!.name)
            }else{
                Log.d("disini2",""+requestCode+" "+resultCode)
            }
        } catch (e: Exception) {
            Toast.makeText(this, "Error, Gagal Mengambil Foto", Toast.LENGTH_LONG).show()
        }
    }
    private fun getData() {
        setupViewModel()
        viewModel.getKategori().observe(this, Observer {
            it?.let { resource ->
                when (resource.status) {
                    Status.SUCCESS -> {
                        kategoriList.clear()
                        kategoriList.addAll(resource.data!!)
                        for(item in kategoriList){
                            adapterKategori.add(item.nama)
                            Log.d("tesDownload",adapterKategori[0])
                        }
                        val adapter = ArrayAdapter(this, R.layout.dropdown_item, adapterKategori)
                        b.spnKategori.adapter = adapter
                        adapter.notifyDataSetChanged()

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


}