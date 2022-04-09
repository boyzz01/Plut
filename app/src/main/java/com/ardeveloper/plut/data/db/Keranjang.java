package com.ardeveloper.plut.data.db;

import com.google.gson.annotations.SerializedName;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;

@Entity(nameInDb = "Keranjang")
public class Keranjang {

	@SerializedName("id")
	private int id;

	@SerializedName("product_id")
	public String p_id;

	@SerializedName("product_price")
	public int p_price;

	@SerializedName("qty")
	public int p_qty;

	@Generated(hash = 1102497273)
	public Keranjang(int id, String p_id, int p_price, int p_qty) {
		this.id = id;
		this.p_id = p_id;
		this.p_price = p_price;
		this.p_qty = p_qty;
	}

	@Generated(hash = 737339970)
	public Keranjang() {
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getP_id() {
		return this.p_id;
	}

	public void setP_id(String p_id) {
		this.p_id = p_id;
	}

	public int getP_price() {
		return this.p_price;
	}

	public void setP_price(int p_price) {
		this.p_price = p_price;
	}

	public int getP_qty() {
		return this.p_qty;
	}

	public void setP_qty(int p_qty) {
		this.p_qty = p_qty;
	}




}