package com.ardeveloper.plut.data.repo

import ApiService
import com.infield.epcs.data.api.ApiHelper

class MainRepository(private val apiHelper: ApiHelper) {

    suspend fun checkUsers(username: String,password:String) = apiHelper.getUsers(username, password)
    suspend fun getKota() = apiHelper.getKota()
    suspend fun getUmkm() = apiHelper.getUmkm()
    suspend fun addUmkm(nama : String,nib : Int,kode:String) = apiHelper.addUmkm(nama = nama,nib=nib, kode =  kode)
    suspend fun getKategori() = apiHelper.getKategori()
    suspend fun getProduk() = apiHelper.getProduk()
    suspend fun getProduk(kode: String) = apiHelper.getProduk(kode)
    suspend fun getProdukDetail(kode: String) = apiHelper.getProdukDetail(kode)
    suspend fun getUmkmDetail(kode: String) = apiHelper.getUmkmDetail(kode)
}