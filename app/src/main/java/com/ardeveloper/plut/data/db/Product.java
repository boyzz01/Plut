package com.ardeveloper.plut.data.db;

import androidx.annotation.Nullable;

import com.google.gson.annotations.SerializedName;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;

import java.io.Serializable;

@Entity(nameInDb = "Product")
public class Product implements Serializable {
	private static final long serialVersionUID = 0L;

	@SerializedName("id")
	public int id;

	@SerializedName("kode_kota")
	public String kodeKota;

	@SerializedName("kode_umkm")
	public String kodeUmkm;

	@SerializedName("kode_kategori")
	public String kodeKategori;

	@SerializedName("kode_produk")
	public String kodeProduk;

	@SerializedName("nama")
	public String nama;

	@SerializedName("harga")
	public int harga;

	@SerializedName("stock")
	public int stock;

	@Nullable
	@SerializedName("nama_umkm")
	public String namaUmkm;

	@SerializedName("foto")
	public String foto;

	@SerializedName("updated_at")
	public String updatedAt;

	@SerializedName("created_at")
	public String createdAt;










	@Generated(hash = 1603614635)
	public Product(int id, String kodeKota, String kodeUmkm, String kodeKategori,
			String kodeProduk, String nama, int harga, int stock, String namaUmkm,
			String foto, String updatedAt, String createdAt) {
		this.id = id;
		this.kodeKota = kodeKota;
		this.kodeUmkm = kodeUmkm;
		this.kodeKategori = kodeKategori;
		this.kodeProduk = kodeProduk;
		this.nama = nama;
		this.harga = harga;
		this.stock = stock;
		this.namaUmkm = namaUmkm;
		this.foto = foto;
		this.updatedAt = updatedAt;
		this.createdAt = createdAt;
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

	public String getNamaUmkm() {
		return this.namaUmkm;
	}

	public void setNamaUmkm(String namaUmkm) {
		this.namaUmkm = namaUmkm;
	}

	
}