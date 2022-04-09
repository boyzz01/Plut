package com.ardeveloper.plut.data.model;

import com.ardeveloper.plut.data.db.Product;
import com.google.gson.annotations.SerializedName;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;

public class Cart {

	@SerializedName("id")
	private int id;

	@SerializedName("position")
	private int position;

	@SerializedName("count")
	public Double count;

	@SerializedName("note")
	public Double note;

	@SerializedName("product")
	public Product product;



}