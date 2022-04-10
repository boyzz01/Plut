package com.ardeveloper.plut.data.db;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;

import java.io.Serializable;

@Entity(nameInDb = "UMKM")
public class UMKM implements Serializable {
    private static final long serialVersionUID = 0L;

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

    @SerializedName("kode_umkm")
    private String kodeUmkm;
    
    @Generated(hash = 155088)
    public UMKM(Integer id, String kode_kota, String nama, Integer nib,
            String kodeUmkm) {
        this.id = id;
        this.kode_kota = kode_kota;
        this.nama = nama;
        this.nib = nib;
        this.kodeUmkm = kodeUmkm;
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
    public String getKodeUmkm() {
        return this.kodeUmkm;
    }
    public void setKodeUmkm(String kodeUmkm) {
        this.kodeUmkm = kodeUmkm;
    }


}
