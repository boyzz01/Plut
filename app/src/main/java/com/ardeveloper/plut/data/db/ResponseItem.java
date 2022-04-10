package com.ardeveloper.plut.data.db;

import com.google.gson.annotations.SerializedName;

public class ResponseItem{

	@SerializedName("jk")
	private String jk;

	@SerializedName("alamat_pemilik")
	private Object alamatPemilik;

	@SerializedName("kode_umkm")
	private String kodeUmkm;

	@SerializedName("noktp")
	private String noktp;

	@SerializedName("nib")
	private int nib;

	@SerializedName("created_at")
	private String createdAt;

	@SerializedName("ttl")
	private String ttl;

	@SerializedName("nama")
	private String nama;

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

	public int getNib(){
		return nib;
	}

	public String getCreatedAt(){
		return createdAt;
	}

	public String getTtl(){
		return ttl;
	}

	public String getNama(){
		return nama;
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
}