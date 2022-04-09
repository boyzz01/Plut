package com.ardeveloper.plut.view

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import com.ardeveloper.plut.BaseActivity
import com.ardeveloper.plut.data.db.User
import com.ardeveloper.plut.databinding.ActivityLoginBinding
import com.ardeveloper.plut.preferences.SharedPrefs
import com.google.gson.Gson
import com.ardeveloper.plut.api.RetrofitBuilder
import es.dmoral.toasty.Toasty
import okhttp3.ResponseBody
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class Login : BaseActivity() {

    private lateinit var binding : ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        setupViewModel()
        initView()

        binding.buttonIdLogin.setOnClickListener {

            if (binding.etUserId.text!!.isEmpty()){
                binding.tilUser.error = "Username tidak boleh kosong"
                binding.etUserId.requestFocus()
                return@setOnClickListener
            }else{
                binding.tilUser.error = ""
            }
            if (binding.etPassword.text!!.isEmpty())
            {
                binding.tilPass.error = "Password tidak boleh kosong"
                binding.etPassword.requestFocus()
                return@setOnClickListener
            }else{
                binding.tilPass.error = ""
            }


            RetrofitBuilder.apiService.checkUser(binding.etUserId.text.toString(),binding.etPassword.text.toString())
                .enqueue(object : Callback<ResponseBody>{
                    override fun onResponse(
                        call: Call<ResponseBody>,
                        response: Response<ResponseBody>
                    ) {

                        val jsonString : String= response.body()!!.string().toString()
                        val jsonResult = JSONObject(jsonString)
                        val sukses : Boolean = jsonResult.optBoolean("success")

                        if (sukses){
                            val gson = Gson()
                            val user : User = gson.fromJson(jsonResult.getJSONObject("data").toString(),User::class.java)
                            Log.d("tesDownload","Error "+user.username)
                            SharedPrefs.save(this@Login,SharedPrefs.LOGIN,true)
                            SharedPrefs.save(this@Login,SharedPrefs.USERNAME,user.username)
                            SharedPrefs.save(this@Login,SharedPrefs.USER_LEVEL,user.level)
                            val intent = Intent(this@Login, MainActivity::class.java)
                            startActivity(intent)
                            finish()
                        }else{
                            Toasty.error(this@Login,"Username/Password Salah").show()
                        }
                    }

                    override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                        Log.d("tesDownload","Error "+t.message)
                    }

                })
//            viewModel.checkUser(binding.etUserId.text.toString(),binding.etPassword.text.toString()).observe(this,
//                Observer {
//                    it?.let { resource ->
//                        when (resource.status) {
//                            Status.SUCCESS -> {
//
//                                Log.d("tesDownload","Sukses"+ it.data.)
//
//                            }
//                            Status.ERROR -> {
//                                Log.d("tesDownload","Error "+it.message)
//
//                            }
//                            Status.LOADING -> {
//                                Log.d("tesDownload",resource.toString())
//                            }
//                        }
//                    }
//                })
//            SharedPrefs.save(this,SharedPrefs.LOGIN,true)
//            val intent = Intent(this, MainActivity::class.java)
//            startActivity(intent)
//            finish()
        }
    }

    private fun initView() {
        binding.etUserId.addTextChangedListener (object : TextWatcher {

            override fun afterTextChanged(s: Editable) {
                binding.tilUser.isErrorEnabled = false
            }

            override fun beforeTextChanged(s: CharSequence, start: Int,
                                           count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence, start: Int,
                                       before: Int, count: Int) {
            }
        })

        binding.etPassword.addTextChangedListener (object : TextWatcher {

            override fun afterTextChanged(s: Editable) {
                binding.tilPass.isErrorEnabled = false
            }

            override fun beforeTextChanged(s: CharSequence, start: Int,
                                           count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence, start: Int,
                                       before: Int, count: Int) {
            }
        })
    }
}