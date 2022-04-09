package com.ardeveloper.plut.data.db;

import java.util.List;
import com.google.gson.annotations.SerializedName;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;

@Entity
public class Kategori{

	@SerializedName("nama")
	private String nama;

	@SerializedName("kode")
	private String kode;

	@SerializedName("id")
	private int id;

	@Generated(hash = 1524957265)
	public Kategori(String nama, String kode, int id) {
		this.nama = nama;
		this.kode = kode;
		this.id = id;
	}

	@Generated(hash = 1434439002)
	public Kategori() {
	}

	public String getNama(){
		return nama;
	}

	public String getKode(){
		return kode;
	}

	public int getId(){
		return id;
	}

	public void setNama(String nama) {
		this.nama = nama;
	}

	public void setKode(String kode) {
		this.kode = kode;
	}

	public void setId(int id) {
		this.id = id;
	}
}