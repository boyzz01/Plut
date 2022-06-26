package com.infield.epcs.data.api

import ApiService


class ApiHelper(private val apiService: ApiService) {

    suspend fun getUsers(username : String,password :String) = apiService.checkUser(username,password)

    suspend fun getKota() = apiService.getKota()
    suspend fun getUmkm() = apiService.getUmkm()
    suspend fun getLaporan() = apiService.getLaporan()
    suspend fun addUmkm(nama : String,nib : Int,kode:String) = apiService.addUmkm(nama = nama,nib=nib, kode =  kode)
    suspend fun getKategori() = apiService.getKategori()
    suspend fun getProduk() = apiService.getProduk()
    suspend fun getHistory() = apiService.getHistory()
    suspend fun getProduk(kode: String) = apiService.getProduk(kode)
    suspend fun getProdukDetail(kode: String) = apiService.getProdukDetail(kode)
    suspend fun getUmkmDetail(kode: String) = apiService.getUmkmDetail(kode)
}