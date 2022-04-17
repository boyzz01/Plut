package com.ardeveloper.plut.view

import ApiService
import `in`.mayanknagwanshi.imagepicker.ImageSelectActivity
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.ardeveloper.plut.BaseActivity
import com.ardeveloper.plut.R
import com.ardeveloper.plut.api.ApiClient
import com.ardeveloper.plut.data.db.Product
import com.ardeveloper.plut.databinding.ActivityOpnameBarangBinding
import com.bumptech.glide.Glide
import com.google.gson.Gson
import es.dmoral.toasty.Toasty
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.ResponseBody
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.File
import java.io.FileOutputStream
import java.io.OutputStream


class OpnameBarang : BaseActivity() {

    private lateinit var b : ActivityOpnameBarangBinding
    private lateinit var product : Product
    private var umkm = ""
    var mediaPath: String? = "N/A"
    var encodedImage: String? = "N/A"
    var imageFile : File? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        b = ActivityOpnameBarangBinding.inflate(layoutInflater)
        val view = b.root
        setContentView(view)

        initView()
        getData()
    }

    private fun getData() {
        Glide.with(this)
            .load(product.foto)
            .error(R.drawable.image_placeholder)
            .placeholder(R.drawable.image_placeholder).centerCrop()
            .into(b.imageView15)
    }

    private fun initView() {
        val intent = intent
        product = (intent.getSerializableExtra("produk") as? Product)!!
        val nama = product?.nama
        umkm = intent.getStringExtra("umkm").toString()
        setSupportActionBar(b.toolbar4)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true);
        supportActionBar!!.setDisplayShowHomeEnabled(true);
        supportActionBar!!.title = "Opname Produk"

        b.kodeProduk.setText(product.kodeProduk)
        b.namaProduk.setText(product.nama)
        b.jumStock.setText(""+product.stock)
        b.hargaProduk.setText(""+product.harga)
        b.namaUmkm.setText(""+product.namaUmkm)



        b.txtChooseImage3.setOnClickListener {
            val intent = Intent(this, ImageSelectActivity::class.java)
            intent.putExtra(ImageSelectActivity.FLAG_COMPRESS, true) //default is true
            intent.putExtra(ImageSelectActivity.FLAG_CAMERA, true) //default is true
            intent.putExtra(ImageSelectActivity.FLAG_GALLERY, true) //default is true

            startActivityForResult(intent, 1213)
        }

        b.update.setOnClickListener {



            if (b.namaProduk.text!!.isEmpty()){
                Toasty.error(this,"Nama Produk tidak boleh kosong",Toast.LENGTH_SHORT).show()
                b.namaProduk.requestFocus()
                return@setOnClickListener
            }
            if (b.hargaProduk.text!!.isEmpty()){
                Toasty.error(this,"Harga tidak boleh kosong",Toast.LENGTH_SHORT).show()
                b.hargaProduk.requestFocus()
                return@setOnClickListener
            }
            if (b.jumStock.text!!.isEmpty()){
                Toasty.error(this,"Stock tidak boleh kosong",Toast.LENGTH_SHORT).show()
                b.jumStock.requestFocus()
                return@setOnClickListener
            }




//            if (imageFile==null){
////                Toasty.error(this,"Mohon Pilih Foto",Toast.LENGTH_SHORT).show()
////                b.imageView15.requestFocus()
////                return@setOnClickListener
//
//                imageFile = File("")
//            }

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



        val kode: RequestBody = RequestBody.create(
            "text/plain".toMediaTypeOrNull(),
            b.kodeProduk.text.toString()
        )

        
        val nama: RequestBody = RequestBody.create(
            "text/plain".toMediaTypeOrNull(),
            b.namaProduk.text.toString()
        )

        val harga: RequestBody = RequestBody.create(
            "text/plain".toMediaTypeOrNull(),
            b.hargaProduk.text.toString()
        )
        
        val stock: RequestBody = RequestBody.create(
            "text/plain".toMediaTypeOrNull(),
            b.jumStock.text.toString()
        )

        if (imageFile==null){
            Log.d("masuk","null")

            apiInterface.editProduk(kode,nama,harga,stock)
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
//                            val produk : Product = gson.fromJson(jsonResult.getJSONObject("data").toString(),
//                                Product::class.java)
                                Toasty.success(this@OpnameBarang,"Produk Berhasil Diupdate").show()
                                //  val intent = Intent(this@OpnameBarang, ProductDetail::class.java)
                                //intent.putExtra("produk", produk!!.kodeProduk)
                                //startActivity(intent)
                                finish()

                            }else{
                                Toasty.error(this@OpnameBarang,"Gagal Menambah Produk").show()
                            }

                        }else{
                            Toasty.error(this@OpnameBarang,"Error : "+response.code()).show()
                        }
                        hideLoadingDialog()
                    }

                    override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                        Toasty.error(this@OpnameBarang,"Error : "+t.message).show()
                        hideLoadingDialog()
                    }

                })

        }else{
            Log.d("masuk","gak")
            val requestFile: RequestBody = RequestBody.create(
                "image/*".toMediaTypeOrNull(), imageFile!!
            )
             val foto = MultipartBody.Part.createFormData("foto", imageFile!!.name, requestFile)
            apiInterface.editProduk(kode,nama,harga, foto,stock)
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
//                            val produk : Product = gson.fromJson(jsonResult.getJSONObject("data").toString(),
//                                Product::class.java)
                                Toasty.success(this@OpnameBarang,"Produk Berhasil Diupdate").show()
                                //  val intent = Intent(this@OpnameBarang, ProductDetail::class.java)
                                //intent.putExtra("produk", produk!!.kodeProduk)
                                //startActivity(intent)
                                finish()

                            }else{
                                Toasty.error(this@OpnameBarang,"Gagal Menambah Produk").show()
                            }

                        }else{
                            Toasty.error(this@OpnameBarang,"Error : "+response.code()).show()
                        }
                        hideLoadingDialog()
                    }

                    override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                        Toasty.error(this@OpnameBarang,"Error : "+t.message).show()
                        hideLoadingDialog()
                    }

                })

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
                b.imageView15.setImageBitmap(selectedImage)
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
}