package com.ardeveloper.plut.data.response;

import com.google.gson.annotations.SerializedName;

public class Transaksi{

	@SerializedName("kembalian")
	private int kembalian;

	@SerializedName("deleted")
	private int deleted;

	@SerializedName("total_harga")
	private int totalHarga;



	@SerializedName("metode")
	private String metode;


	@SerializedName("bank")
	private String bank;


	@SerializedName("nokartu")
	private String nokartu;

	@SerializedName("subtotal")
	private int subtotal;

	@SerializedName("updated_at")
	private String updatedAt;

	@SerializedName("uang_diterima")
	private int uangDiterima;

	@SerializedName("created_at")
	private String createdAt;

	@SerializedName("id")
	private int id;

	@SerializedName("id_transaksi")
	private String idTransaksi;

	@SerializedName("id_user")
	private int idUser;

	@SerializedName("total_produk")
	private int totalProduk;

	@SerializedName("diskon")
	private int diskon;

	@SerializedName("username")
	private String username;

	public String  getUsername() {
		return username;
	}


	public String getMetode() {
		return metode;
	}

	public String getBank() {
		return bank;
	}

	public String getNokartu() {
		return nokartu;
	}

	public int getSubtotal() {
		return subtotal;
	}
	public int getKembalian(){
		return kembalian;
	}

	public int getDeleted(){
		return deleted;
	}

	public int getTotalHarga(){
		return totalHarga;
	}

	public String getUpdatedAt(){
		return updatedAt;
	}

	public int getUangDiterima(){
		return uangDiterima;
	}

	public String getCreatedAt(){
		return createdAt;
	}

	public int getId(){
		return id;
	}

	public String getIdTransaksi(){
		return idTransaksi;
	}

	public int getIdUser(){
		return idUser;
	}

	public int getTotalProduk(){
		return totalProduk;
	}

	public int getDiskon(){
		return diskon;
	}
}