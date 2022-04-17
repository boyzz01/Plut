package com.ardeveloper.plut.data.response;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class ResponseShop {

	@SerializedName("total")
	private int total;

	@SerializedName("produk")
	private List<ResponseProduk> produk;

	public int getTotal(){
		return total;
	}

	public List<ResponseProduk> getProduk(){
		return produk;
	}
}