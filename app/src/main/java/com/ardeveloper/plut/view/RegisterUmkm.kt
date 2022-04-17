package com.ardeveloper.plut.view

import ApiService
import android.app.DatePickerDialog
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
import com.ardeveloper.plut.databinding.ActivityRegisterUmkmBinding
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
    var mediaPath: String? = "N/A"
    var encodedImage: String? = "N/A"
    lateinit var imageFile : File
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


//        b.txtChooseImage.setOnClickListener{
//            val intent = Intent(this, ImageSelectActivity::class.java)
//            intent.putExtra(ImageSelectActivity.FLAG_COMPRESS, true) //default is true
//            intent.putExtra(ImageSelectActivity.FLAG_CAMERA, true) //default is true
//            intent.putExtra(ImageSelectActivity.FLAG_GALLERY, true) //default is true
//
//            startActivityForResult(intent, 1213)
//        }





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
//                b.imageUmkm.setImageBitmap(selectedImage)
//                encodedImage = encodeImage(selectedImage)
//
//                imageFile = File(mediaPath)
//                Log.d("imagefile",""+imageFile.name)
//            }else{
//                Log.d("disini2",""+requestCode+" "+resultCode)
//            }
//        } catch (e: Exception) {
//            Toast.makeText(this, "Error, Gagal Mengambil Foto", Toast.LENGTH_LONG).show()
//        }
//    }

    private fun uploadData() {

        showLoadingDialog()
        val apiInterface = ApiClient.getClient(this).create(ApiService::class.java)
        val nib = b.etNIB.text.toString().toInt()
//
//
//        val requestFile: RequestBody = RequestBody.create(
//            "image/*".toMediaTypeOrNull(),imageFile
//        )
//
//        val nama: RequestBody = RequestBody.create(
//            "text/plain".toMediaTypeOrNull(),
//            b.etNama.text.toString()
//        )
//
//        val nibText: RequestBody = RequestBody.create(
//            "text/plain".toMediaTypeOrNull(),
//            b.etNIB.text.toString()
//        )
//        val kode: RequestBody = RequestBody.create(
//            "text/plain".toMediaTypeOrNull(),
//            b.spnKota.selectedItem.toString()
//        )
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

        apiInterface.addUmkm(b.etNama.text.toString(),nib,b.spnKota.selectedItem.toString())
            .enqueue(object : Callback<ResponseBody> {
                override fun onResponse(
                    call: Call<ResponseBody>,
                    response: Response<ResponseBody>
                ) {
                  //  val response_data = response.body()?.string();
                    if (response.isSuccessful){
                        Toasty.success(this@RegisterUmkm,"UMKM Berhasil Ditambahkan").show()
                        resetAll()
                    }else{
                        Toasty.error(this@RegisterUmkm,"Error : "+response.code()).show()
                    }
                    hideLoadingDialog()
                }

                override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                    Toasty.error(this@RegisterUmkm,"Error : "+t.message).show()
                    hideLoadingDialog()
                }

            })
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
        b.spnKota.setSelection(0)
        b.etNIB.setText("")
        b.etNIB.clearFocus()
        b.etNama.setText("")

    }


    private fun initView() {
        setSupportActionBar(b.toolbar2)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true);
        supportActionBar!!.setDisplayShowHomeEnabled(true);
        val adapter = ArrayAdapter(this, R.layout.dropdown_item, adapterKota)
        b.spnKota.setTitle("Pilih Kabupaten Kota")
        b.spnKota.setPositiveButton("Ok")
        b.spnKota.adapter = adapter


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