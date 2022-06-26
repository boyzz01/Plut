package com.ardeveloper.plut.data.model;

import com.google.gson.annotations.SerializedName;

public class DetailProductResponse {

	@SerializedName("jk")
	private String jk;

	@SerializedName("alamat_pemilik")
	private Object alamatPemilik;

	@SerializedName("kode_umkm")
	private String kodeUmkm;

	@SerializedName("noktp")
	private String noktp;

	@SerializedName("nib")
	private String nib;

	@SerializedName("created_at")
	private String createdAt;

	@SerializedName("kode_kategori")
	private String kodeKategori;

	@SerializedName("kode_produk")
	private String kodeProduk;

	@SerializedName("ttl")
	private String ttl;

	@SerializedName("total")
	private int total;

	@SerializedName("nama")
	private String nama;

	@SerializedName("harga")
	private int harga;

	@SerializedName("foto")
	private String foto;

	@SerializedName("updated_at")
	private String updatedAt;

	@SerializedName("kode_kota")
	private String kodeKota;

	@SerializedName("pemilik")
	private Object pemilik;

	@SerializedName("nohp")
	private String nohp;

	@SerializedName("id")
	private int id;

	@SerializedName("stock")
	private int stock;

	public String getJk(){
		return jk;
	}

	public Object getAlamatPemilik(){
		return alamatPemilik;
	}

	public String getKodeUmkm(){
		return kodeUmkm;
	}

	public String getNoktp(){
		return noktp;
	}

	public String getNib(){
		return nib;
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

	public String getTtl(){
		return ttl;
	}

	public int getTotal(){
		return total;
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

	public String getKodeKota(){
		return kodeKota;
	}

	public Object getPemilik(){
		return pemilik;
	}

	public String getNohp(){
		return nohp;
	}

	public int getId(){
		return id;
	}

	public int getStock(){
		return stock;
	}
}