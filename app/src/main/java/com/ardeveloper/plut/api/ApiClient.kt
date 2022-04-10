package com.ardeveloper.plut.api


import android.content.Context
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class ApiClient {
    companion object {
        fun getClient(context: Context): Retrofit {

          val BASE_URL = "https://ardisahputra.me/plut/api/"
          //  val BASE_URL = "https://ardisahputra.tech/epcs/"


            val clientBuilder: OkHttpClient.Builder = buildClient()
            return Retrofit.Builder().
            baseUrl(BASE_URL)
                    .client(clientBuilder
                            .connectTimeout(1 , TimeUnit.MINUTES)
                            .writeTimeout(1 , TimeUnit.MINUTES)
                            .readTimeout(1 , TimeUnit.MINUTES)
                            .addInterceptor { chain ->
                                val newRequest = chain.request().newBuilder()
                                        .build()
                                chain.proceed(newRequest)
                            }
                            .retryOnConnectionFailure(true)
                            .build())
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()

        }

        private fun buildClient(): OkHttpClient.Builder {
            val clientBuilder = OkHttpClient.Builder()
            val loggingInterceptor = HttpLoggingInterceptor()
            loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
            clientBuilder.addInterceptor(loggingInterceptor)
                    .connectTimeout(5 , TimeUnit.MINUTES)
            return clientBuilder
        }
    }


}