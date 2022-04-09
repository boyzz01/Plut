package com.ardeveloper.plut.data.db;

import com.google.gson.annotations.SerializedName;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;

@Entity(nameInDb = "Kota")
public class Kota {


	@SerializedName("id")
	private int id;

	@SerializedName("nama")
	private String nama;

	@SerializedName("kode")
	private String kode;

	@Generated(hash = 1041416952)
	public Kota(int id, String nama, String kode) {
		this.id = id;
		this.nama = nama;
		this.kode = kode;
	}

	@Generated(hash = 1555707845)
	public Kota() {
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getNama() {
		return this.nama;
	}

	public void setNama(String nama) {
		this.nama = nama;
	}

	public String getKode() {
		return this.kode;
	}

	public void setKode(String kode) {
		this.kode = kode;
	}


}