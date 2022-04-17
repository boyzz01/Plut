package com.ardeveloper.plut.data.response;

import com.google.gson.annotations.SerializedName;

public class ResponseProduk {

	@SerializedName("kode_umkm")
	private String kodeUmkm;

	@SerializedName("created_at")
	private String createdAt;

	@SerializedName("kode_kategori")
	private String kodeKategori;

	@SerializedName("kode_produk")
	private String kodeProduk;

	@SerializedName("nama")
	private String nama;

	@SerializedName("harga")
	private int harga;

	@SerializedName("foto")
	private String foto;

	@SerializedName("updated_at")
	private String updatedAt;

	@SerializedName("jumlah")
	private int jumlah;

	@SerializedName("user_id")
	private int userId;

	@SerializedName("product_id")
	private String productId;

	@SerializedName("id_cart")
	private int idCart;

	@SerializedName("kode_kota")
	private String kodeKota;

	@SerializedName("id")
	private int id;

	@SerializedName("stock")
	private int stock;

	public String getKodeUmkm(){
		return kodeUmkm;
	}

	public String getCreatedAt(){
		return createdAt;
	}

	public String getKodeKategori(){
		return kodeKategori;
	}

	public String getKodeProduk(){
		return kodeProduk;
	}

	public String getNama(){
		return nama;
	}

	public int getHarga(){
		return harga;
	}

	public String getFoto(){
		return foto;
	}

	public String getUpdatedAt(){
		return updatedAt;
	}

	public int getJumlah(){
		return jumlah;
	}

	public int getUserId(){
		return userId;
	}

	public String getProductId(){
		return productId;
	}

	public int getIdCart(){
		return idCart;
	}

	public String getKodeKota(){
		return kodeKota;
	}

	public int getId(){
		return id;
	}

	public int getStock(){
		return stock;
	}
}