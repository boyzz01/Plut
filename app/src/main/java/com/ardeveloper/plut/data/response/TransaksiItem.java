package com.ardeveloper.plut.data.response;

import androidx.annotation.Nullable;

import com.google.gson.annotations.SerializedName;

public class TransaksiItem {

	@SerializedName("id_product")
	private String idProduct;

	@SerializedName("deleted")
	private int deleted;

	@SerializedName("updated_at")
	private String updatedAt;

	@SerializedName("created_at")
	private String createdAt;

	@SerializedName("id_umkm")
	private int idUmkm;

	@SerializedName("total_harga")
	private int totalHarga;

	@SerializedName("harga")
	private int harga;

	@SerializedName("nama")
	private String nama;

	@SerializedName("id")
	private int id;

	@SerializedName("id_transaksi")
	private String idTransaksi;

	@SerializedName("total_produk")
	private int totalProduk;


	@Nullable
	@SerializedName("foto")
	private String foto;


	@Nullable
	public String getFoto() {
		return foto;
	}



	public int getHarga() {
		return harga;
	}

	public String getNama() {
		return nama;
	}

	public String getIdProduct(){
		return idProduct;
	}

	public int getDeleted(){
		return deleted;
	}

	public String getUpdatedAt(){
		return updatedAt;
	}

	public String getCreatedAt(){
		return createdAt;
	}

	public int getIdUmkm(){
		return idUmkm;
	}

	public int getTotalHarga(){
		return totalHarga;
	}

	public int getId(){
		return id;
	}

	public String getIdTransaksi(){
		return idTransaksi;
	}

	public int getTotalProduk(){
		return totalProduk;
	}
}