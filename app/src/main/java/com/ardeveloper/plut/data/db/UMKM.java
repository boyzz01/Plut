package com.ardeveloper.plut.data.db;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;

@Entity(nameInDb = "UMKM")
public class UMKM {


    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("kode_kota")
    @Expose
    private String kode_kota;
    @SerializedName("nama")
    @Expose
    private String nama;
    @SerializedName("nib")
    @Expose
    private Integer nib;
    @Generated(hash = 446311185)
    public UMKM(Integer id, String kode_kota, String nama, Integer nib) {
        this.id = id;
        this.kode_kota = kode_kota;
        this.nama = nama;
        this.nib = nib;
    }
    @Generated(hash = 2128528321)
    public UMKM() {
    }
    public Integer getId() {
        return this.id;
    }
    public void setId(Integer id) {
        this.id = id;
    }
    public String getKode_kota() {
        return this.kode_kota;
    }
    public void setKode_kota(String kode_kota) {
        this.kode_kota = kode_kota;
    }
    public String getNama() {
        return this.nama;
    }
    public void setNama(String nama) {
        this.nama = nama;
    }
    public Integer getNib() {
        return this.nib;
    }
    public void setNib(Integer nib) {
        this.nib = nib;
    }


}
