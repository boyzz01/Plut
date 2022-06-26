package com.ardeveloper.plut.view

import ApiService

import android.app.Activity
import android.app.DatePickerDialog
import android.content.Intent
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.DatePicker
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.Observer
import com.ardeveloper.plut.BaseActivity
import com.ardeveloper.plut.R
import com.ardeveloper.plut.api.ApiClient
import com.ardeveloper.plut.data.db.Kota
import com.ardeveloper.plut.data.db.UMKM
import com.ardeveloper.plut.databinding.ActivityRegisterUmkmBinding
import com.github.dhaval2404.imagepicker.ImagePicker
import com.infield.epcs.utils.Status
import com.wdullaer.materialdatetimepicker.Utils
import es.dmoral.toasty.Toasty
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.File
import java.text.SimpleDateFormat
import java.util.*


class RegisterUmkm : BaseActivity(){

    private lateinit var b : ActivityRegisterUmkmBinding
    private lateinit var listKota : List<Kota>
    private lateinit var adapterKota : MutableList<String>
    var mediaPath: String? = ""
    var encodedImage: String? = "N/A"
    var imageFile : File? = null
    var cal = Calendar.getInstance()




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        b = ActivityRegisterUmkmBinding.inflate(layoutInflater)
        val view = b.root
        setContentView(view)

        listKota = ArrayList()
        adapterKota = ArrayList()
        setupViewModel()
        initView()
        getData()

       b.simpan.setOnClickListener {
            if (b.spnKota.selectedItem==null){
                Toasty.error(this,"Mohon pilih kota terlebih dahulu",Toast.LENGTH_SHORT).show()
                b.spnKota.requestFocus()
                return@setOnClickListener
            }
           if (b.etNama.text!!.isEmpty()){
               Toasty.error(this,"Nama UMKM tidak boleh kosong",Toast.LENGTH_SHORT).show()
               b.etNama.requestFocus()
               return@setOnClickListener
           }

           if (b.etNIB.text!!.isEmpty()){
               Toasty.error(this,"Nomor NIB tidak boleh kosong",Toast.LENGTH_SHORT).show()
               b.etNIB.requestFocus()
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


        b.txtChooseImage4.setOnClickListener{
//            val intent = Intent(this, ImageSelectActivity::class.java)
//            intent.putExtra(ImageSelectActivity.FLAG_COMPRESS, true) //default is true
//            intent.putExtra(ImageSelectActivity.FLAG_CAMERA, true) //default is true
//            intent.putExtra(ImageSelectActivity.FLAG_GALLERY, true) //default is true

//            startActivityForResult(intent, 1213)

            ImagePicker.with(this)
                .crop()	    			//Crop image(Optional), Check Customization for more option
                .compress(1024)			//Final image size will be less than 1 MB(Optional)
                .maxResultSize(1080, 1080)	//Final image resolution will be less than 1080 x 1080(Optional)
                .saveDir(getExternalFilesDir("plut")!!)
                .start()
        }



    }

//    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
//        super.onActivityResult(requestCode, resultCode, data)
//        try {
//
//            // When an Image is picked
//            if (requestCode == 1213 && resultCode == RESULT_OK && null != data) {
//                mediaPath = data.getStringExtra(ImageSelectActivity.RESULT_FILE_PATH)
//                Log.d("disini",mediaPath.toString())
//                val selectedImage = BitmapFactory.decodeFile(mediaPath)
//                b.imageUmkm3.setImageBitmap(selectedImage)
//                encodedImage = encodeImage(selectedImage)
//
//                imageFile = File(mediaPath)
//                Log.d("imagefile",""+ imageFile!!.name)
//            }else{
//                Log.d("disini2",""+requestCode+" "+resultCode)
//            }
//        } catch (e: Exception) {
//            Toast.makeText(this, "Error, Gagal Mengambil Foto", Toast.LENGTH_LONG).show()
//        }
//    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK) {
            //Image Uri will not be null for RESULT_OK
            val uri: Uri = data?.data!!
            imageFile = File(uri.path)
            mediaPath = uri.path
            Log.d("tesfoto",uri.path.toString())
            // Use Uri object instead of File to avoid storage permissions
            b.imageUmkm3.setImageURI(uri)
        } else if (resultCode == ImagePicker.RESULT_ERROR) {
            Toast.makeText(this, ImagePicker.getError(data), Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(this, "Task Cancelled", Toast.LENGTH_SHORT).show()
        }
    }

    private fun uploadData() {

        showLoadingDialog()
        val apiInterface = ApiClient.getClient(this).create(ApiService::class.java)


        val pemilik = createPartFromString(b.pemilikEt.text.toString())
        val alamatPemilik = createPartFromString(b.alamatPemilik.text.toString())
        val tempatL = b.tempatLahir.text.toString()
        val tglLahir = b.tglLahir.text.toString()
        val ttl = createPartFromString("$tempatL, $tglLahir")
        val jk = createPartFromString(b.jenisKelamin.text.toString())
        val nohp = createPartFromString(b.noHp.text.toString())
        val noktp = createPartFromString(b.ktpEt.text.toString())

        val nama = createPartFromString(b.etNama.text.toString())
        val alamatUmkm = createPartFromString(b.alamatUmkm.text.toString())
        val jenisProduk = createPartFromString(b.jenisProduk.text.toString())
        val deskripsiProduk = createPartFromString(b.deskripsiProduk.text.toString())
        val nib = createPartFromString(b.etNIB.text.toString())
        val noHalal = createPartFromString(b.noHalal.text.toString())
        val noBpom = createPartFromString(b.noBpom.text.toString())
        val noPirt = createPartFromString(b.noPirt.text.toString())
        val merekDagang = createPartFromString(b.merkDagang.text.toString())
        val hakCipta = createPartFromString(b.hakCipta.text.toString())
        val email = createPartFromString(b.email.text.toString())
        val fb = createPartFromString(b.fb.text.toString())
        val instagram = createPartFromString(b.ig.text.toString())
        val landingPage = createPartFromString(b.website.text.toString())
        val shopee = createPartFromString(b.shopee.text.toString())
        val tokopedia = createPartFromString(b.tokopedia.text.toString())
        val lain = createPartFromString(b.lain.text.toString())


        val nilai_asset = createPartFromString(b.nilaiAsset.text.toString())
        val omset = createPartFromString(b.omset.text.toString())
        val karyawan = createPartFromString(b.karyawan.text.toString())
        val tiktok = createPartFromString(b.tiktok.text.toString())
        val youtube = createPartFromString(b.youtube.text.toString())
        val sosmedlain = createPartFromString(b.sosmedlainnya.text.toString())
        val lpse = createPartFromString(b.lpse.text.toString())
        val mbiz = createPartFromString(b.mbiz.text.toString())


        val kode = createPartFromString(b.spnKota.selectedItem.toString())
        if (imageFile!=null){
            val foto = createPartFromFile(mediaPath,"foto")
            apiInterface.tambahUmkm(
                pemilik,alamatPemilik,ttl,jk,nohp,noktp,nama,alamatUmkm,jenisProduk,
                deskripsiProduk,nib,noHalal,noBpom,noPirt,merekDagang,hakCipta,email,
                fb,instagram,landingPage,shopee,tokopedia,lain, foto!!,kode, nilai_asset, omset, karyawan, tiktok, youtube, sosmedlain, lpse, mbiz
            ).enqueue(object :  Callback<ResponseBody>{
                override fun onResponse(
                    call: Call<ResponseBody>,
                    response: Response<ResponseBody>
                ) {
                    if (response.isSuccessful){
                        Toasty.success(this@RegisterUmkm,"UMKM Berhasil Ditambahkan").show()
                        resetAll()
                    }else{
                        Toasty.error(this@RegisterUmkm,"Error : "+response.code()+" : "+response.body()).show()
                    }
                    hideLoadingDialog()
                }

                override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                    Toasty.error(this@RegisterUmkm,"Error : "+t.message).show()
                    hideLoadingDialog()
                }

            } )
        }else{
            apiInterface.tambahUmkm(
                pemilik,alamatPemilik,ttl,jk,nohp,noktp,nama,alamatUmkm,jenisProduk,
                deskripsiProduk,nib,noHalal,noBpom,noPirt,merekDagang,hakCipta,email,
                fb,instagram,landingPage,shopee,tokopedia,lain,kode, nilai_asset, omset, karyawan, tiktok, youtube, sosmedlain, lpse, mbiz
            ).enqueue(object :  Callback<ResponseBody>{
                override fun onResponse(
                    call: Call<ResponseBody>,
                    response: Response<ResponseBody>
                ) {
                    if (response.isSuccessful){
                        Toasty.success(this@RegisterUmkm,"UMKM Berhasil Ditambahkan").show()
                        resetAll()
                    }else{
                        Toasty.error(this@RegisterUmkm,"Error : "+response.code()+" : "+response.body()).show()
                    }
                    hideLoadingDialog()
                }

                override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                    Toasty.error(this@RegisterUmkm,"Error : "+t.message).show()
                    hideLoadingDialog()
                }

            } )
        }
//
//        val foto = MultipartBody.Part.createFormData("foto", imageFile.name, requestFile)
//
//        apiInterface.addUmkmWithFoto(nama,nibText,foto,kode)
//            .enqueue(object : Callback<ResponseBody> {
//                override fun onResponse(
//                    call: Call<ResponseBody>,
//                    response: Response<ResponseBody>
//                ) {
//                    Log.d("tesDownload","Sukses"+response.message())
//                    Toasty.success(this@RegisterUmkm,"UMKM Berhasil Ditambahkan").show()
//                }
//
//                override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
//                    Toasty.error(this@RegisterUmkm,"Error : "+t.message).show()
//                }
//
//            })

//        apiInterface.addUmkm(b.etNama.text.toString(),nib,b.spnKota.selectedItem.toString())
//            .enqueue(object : Callback<ResponseBody> {
//                override fun onResponse(
//                    call: Call<ResponseBody>,
//                    response: Response<ResponseBody>
//                ) {
//                  //  val response_data = response.body()?.string();
//                    if (response.isSuccessful){
//                        Toasty.success(this@RegisterUmkm,"UMKM Berhasil Ditambahkan").show()
//                        resetAll()
//                    }else{
//                        Toasty.error(this@RegisterUmkm,"Error : "+response.code()).show()
//                    }
//                    hideLoadingDialog()
//                }
//
//                override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
//                    Toasty.error(this@RegisterUmkm,"Error : "+t.message).show()
//                    hideLoadingDialog()
//                }
//
//            })
//        viewModel.addUmkm(b.etNama.text.toString(),nib,b.spnKota.selectedItem.toString()).observe(this, Observer {
//            it?.let { resource ->
//                when (resource.status) {
//                    Status.SUCCESS -> {
//                        Log.d("tesDownload","Sukses"+resource.data!!.data)
//                        Toasty.success(this,"UMKM Berhasil Ditambahkan").show()
//                    }
//                    Status.ERROR -> {
//                        Log.d("tesDownload","Error "+it.message)
//
//                    }
//                    Status.LOADING -> {
//                        Log.d("tesDownload",resource.toString())
//                    }
//                }
//            }
//        })
    }

    private fun resetAll() {
//        b.spnKota.setSelection(0)
//        b.etNIB.setText("")
//        b.etNIB.clearFocus()
//        b.etNama.setText("")
        finish()
    }


    private fun initView() {
        setSupportActionBar(b.toolbar2)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true);
        supportActionBar!!.setDisplayShowHomeEnabled(true);
        val adapter = ArrayAdapter(this, R.layout.dropdown_item, adapterKota)
        b.spnKota.setTitle("Pilih Kabupaten Kota")
        b.spnKota.setPositiveButton("Ok")
        b.spnKota.adapter = adapter

        val ket = arrayOf("Laki-Laki","Perempuan")
        val adapterJk = ArrayAdapter(this, R.layout.dropdown_item, ket)
        b.jenisKelamin.setAdapter(adapterJk)

        val dateSetListener = object : DatePickerDialog.OnDateSetListener {
            override fun onDateSet(view: DatePicker, year: Int, monthOfYear: Int,
                                   dayOfMonth: Int) {
                cal.set(Calendar.YEAR, year)
                cal.set(Calendar.MONTH, monthOfYear)
                cal.set(Calendar.DAY_OF_MONTH, dayOfMonth)
                updateDateInView()
            }
        }

        b.tglLahir.setOnClickListener {
            DatePickerDialog(this@RegisterUmkm,
                dateSetListener,
                // set DatePickerDialog to point to today's date when it loads up
                cal.get(Calendar.YEAR),
                cal.get(Calendar.MONTH),
                cal.get(Calendar.DAY_OF_MONTH)).show()
        }

//        val drawable : Drawable = resources.getDrawable(R.drawable.image_placeholder)

    }
    private fun updateDateInView() {
        val myFormat = "dd/MM/yyyy" // mention the format you need
        val locale = Locale("id", "ID")
        val sdf = SimpleDateFormat(myFormat, locale)
        b.tglLahir.setText( sdf.format(cal.time))

    }
    private fun getData() {
        showLoadingDialog()
        viewModel.getKota().observe(this, Observer {
            it?.let { resource ->
                when (resource.status) {
                    Status.SUCCESS -> {
                        Log.d("tesDownload","Sukses"+resource.data!!)
                        listKota = resource.data
                        Log.d("tesDownload",""+listKota.size)
                        for(item in listKota){
                            adapterKota.add(item.nama)
                            Log.d("tesDownload",adapterKota[0])
                        }
                        val adapter = ArrayAdapter(this, R.layout.dropdown_item, adapterKota)
                        b.spnKota.adapter = adapter
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




}