package com.ardeveloper.plut.data.db;

import com.google.gson.annotations.SerializedName;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;

@Entity(nameInDb = "Product")
public class Product {

	@SerializedName("kode_umkm")
	public String kodeUmkm;

	@SerializedName("nama")
	public String nama;

	@SerializedName("harga")
	public int harga;

	@SerializedName("foto")
	public String foto;

	@SerializedName("updated_at")
	public String updatedAt;

	@SerializedName("kode_kota")
	public String kodeKota;

	@SerializedName("created_at")
	public String createdAt;

	@SerializedName("id")
	public int id;

	@SerializedName("kode_kategori")
	public String kodeKategori;

	@SerializedName("stock")
	public int stock;

	@SerializedName("kode_produk")
	public String kodeProduk;

	@Generated(hash = 568478311)
	public Product(String kodeUmkm, String nama, int harga, String foto,
			String updatedAt, String kodeKota, String createdAt, int id,
			String kodeKategori, int stock, String kodeProduk) {
		this.kodeUmkm = kodeUmkm;
		this.nama = nama;
		this.harga = harga;
		this.foto = foto;
		this.updatedAt = updatedAt;
		this.kodeKota = kodeKota;
		this.createdAt = createdAt;
		this.id = id;
		this.kodeKategori = kodeKategori;
		this.stock = stock;
		this.kodeProduk = kodeProduk;
	}

	@Generated(hash = 1890278724)
	public Product() {
	}

	public String getKodeUmkm() {
		return this.kodeUmkm;
	}

	public void setKodeUmkm(String kodeUmkm) {
		this.kodeUmkm = kodeUmkm;
	}

	public String getNama() {
		return this.nama;
	}

	public void setNama(String nama) {
		this.nama = nama;
	}

	public int getHarga() {
		return this.harga;
	}

	public void setHarga(int harga) {
		this.harga = harga;
	}

	public String getFoto() {
		return this.foto;
	}

	public void setFoto(String foto) {
		this.foto = foto;
	}

	public String getUpdatedAt() {
		return this.updatedAt;
	}

	public void setUpdatedAt(String updatedAt) {
		this.updatedAt = updatedAt;
	}

	public String getKodeKota() {
		return this.kodeKota;
	}

	public void setKodeKota(String kodeKota) {
		this.kodeKota = kodeKota;
	}

	public String getCreatedAt() {
		return this.createdAt;
	}

	public void setCreatedAt(String createdAt) {
		this.createdAt = createdAt;
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getKodeKategori() {
		return this.kodeKategori;
	}

	public void setKodeKategori(String kodeKategori) {
		this.kodeKategori = kodeKategori;
	}

	public int getStock() {
		return this.stock;
	}

	public void setStock(int stock) {
		this.stock = stock;
	}

	public String getKodeProduk() {
		return this.kodeProduk;
	}

	public void setKodeProduk(String kodeProduk) {
		this.kodeProduk = kodeProduk;
	}

	
}