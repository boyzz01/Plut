package com.ardeveloper.plut.data.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ModelUmkmItem{

	@SerializedName("id")
	@Expose
	private Integer id;
	@SerializedName("kode_umkm")
	@Expose
	private String kodeUmkm;
	@SerializedName("total")
	@Expose
	private Integer total;
	@SerializedName("nama")
	@Expose
	private String nama;
	@SerializedName("kode_kota")
	@Expose
	private String kodeKota;
	@SerializedName("nib")
	@Expose
	private String nib;
	@SerializedName("foto")
	@Expose
	private String foto;
	@SerializedName("pemilik")
	@Expose
	private String pemilik;
	@SerializedName("alamat_pemilik")
	@Expose
	private String alamatPemilik;
	@SerializedName("jk")
	@Expose
	private String jk;
	@SerializedName("ttl")
	@Expose
	private String ttl;
	@SerializedName("nohp")
	@Expose
	private String nohp;
	@SerializedName("noktp")
	@Expose
	private String noktp;
	@SerializedName("alamat_umkm")
	@Expose
	private String alamatUmkm;
	@SerializedName("jenis_produk")
	@Expose
	private String jenisProduk;
	@SerializedName("deskripsi_produk")
	@Expose
	private String deskripsiProduk;
	@SerializedName("no_halal")
	@Expose
	private String noHalal;
	@SerializedName("no_bpom")
	@Expose
	private String noBpom;
	@SerializedName("no_pirt")
	@Expose
	private String noPirt;
	@SerializedName("merek_dagang")
	@Expose
	private String merekDagang;
	@SerializedName("hak_cipta")
	@Expose
	private String hakCipta;
	@SerializedName("email")
	@Expose
	private String email;
	@SerializedName("fb")
	@Expose
	private String fb;
	@SerializedName("instagram")
	@Expose
	private String instagram;
	@SerializedName("landing page")
	@Expose
	private String landingPage;
	@SerializedName("shopee")
	@Expose
	private String shopee;
	@SerializedName("tokopedia")
	@Expose
	private String tokopedia;
	@SerializedName("lain")
	@Expose
	private String lain;
	@SerializedName("created_at")
	@Expose
	private String createdAt;
	@SerializedName("updated_at")
	@Expose
	private String updatedAt;
	@SerializedName("deleted")
	@Expose
	private Integer deleted;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getKodeUmkm() {
		return kodeUmkm;
	}

	public void setKodeUmkm(String kodeUmkm) {
		this.kodeUmkm = kodeUmkm;
	}

	public Integer getTotal() {
		return total;
	}

	public void setTotal(Integer total) {
		this.total = total;
	}

	public String getNama() {
		return nama;
	}

	public void setNama(String nama) {
		this.nama = nama;
	}

	public String getKodeKota() {
		return kodeKota;
	}

	public void setKodeKota(String kodeKota) {
		this.kodeKota = kodeKota;
	}

	public String getNib() {
		return nib;
	}

	public void setNib(String nib) {
		this.nib = nib;
	}

	public String getFoto() {
		return foto;
	}

	public void setFoto(String foto) {
		this.foto = foto;
	}

	public String getPemilik() {
		return pemilik;
	}

	public void setPemilik(String pemilik) {
		this.pemilik = pemilik;
	}

	public String getAlamatPemilik() {
		return alamatPemilik;
	}

	public void setAlamatPemilik(String alamatPemilik) {
		this.alamatPemilik = alamatPemilik;
	}

	public String getJk() {
		return jk;
	}

	public void setJk(String jk) {
		this.jk = jk;
	}

	public String getTtl() {
		return ttl;
	}

	public void setTtl(String ttl) {
		this.ttl = ttl;
	}

	public String getNohp() {
		return nohp;
	}

	public void setNohp(String nohp) {
		this.nohp = nohp;
	}

	public String getNoktp() {
		return noktp;
	}

	public void setNoktp(String noktp) {
		this.noktp = noktp;
	}

	public String getAlamatUmkm() {
		return alamatUmkm;
	}

	public void setAlamatUmkm(String alamatUmkm) {
		this.alamatUmkm = alamatUmkm;
	}

	public String getJenisProduk() {
		return jenisProduk;
	}

	public void setJenisProduk(String jenisProduk) {
		this.jenisProduk = jenisProduk;
	}

	public String getDeskripsiProduk() {
		return deskripsiProduk;
	}

	public void setDeskripsiProduk(String deskripsiProduk) {
		this.deskripsiProduk = deskripsiProduk;
	}

	public String getNoHalal() {
		return noHalal;
	}

	public void setNoHalal(String noHalal) {
		this.noHalal = noHalal;
	}

	public String getNoBpom() {
		return noBpom;
	}

	public void setNoBpom(String noBpom) {
		this.noBpom = noBpom;
	}

	public String getNoPirt() {
		return noPirt;
	}

	public void setNoPirt(String noPirt) {
		this.noPirt = noPirt;
	}

	public String getMerekDagang() {
		return merekDagang;
	}

	public void setMerekDagang(String merekDagang) {
		this.merekDagang = merekDagang;
	}

	public String getHakCipta() {
		return hakCipta;
	}

	public void setHakCipta(String hakCipta) {
		this.hakCipta = hakCipta;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getFb() {
		return fb;
	}

	public void setFb(String fb) {
		this.fb = fb;
	}

	public String getInstagram() {
		return instagram;
	}

	public void setInstagram(String instagram) {
		this.instagram = instagram;
	}

	public String getLandingPage() {
		return landingPage;
	}

	public void setLandingPage(String landingPage) {
		this.landingPage = landingPage;
	}

	public String getShopee() {
		return shopee;
	}

	public void setShopee(String shopee) {
		this.shopee = shopee;
	}

	public String getTokopedia() {
		return tokopedia;
	}

	public void setTokopedia(String tokopedia) {
		this.tokopedia = tokopedia;
	}

	public String getLain() {
		return lain;
	}

	public void setLain(String lain) {
		this.lain = lain;
	}

	public String getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(String createdAt) {
		this.createdAt = createdAt;
	}

	public String getUpdatedAt() {
		return updatedAt;
	}

	public void setUpdatedAt(String updatedAt) {
		this.updatedAt = updatedAt;
	}

	public Integer getDeleted() {
		return deleted;
	}

	public void setDeleted(Integer deleted) {
		this.deleted = deleted;
	}

}