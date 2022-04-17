package com.ardeveloper.plut.data.response;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class ResponseCart{

	@SerializedName("total_item")
	private String totalItem;

	@SerializedName("produk")
	private List<ResponseProduk> produk;

	@SerializedName("total_harga")
	private String totalHarga;

	public String getTotalItem(){
		return totalItem;
	}

	public List<ResponseProduk> getProduk(){
		return produk;
	}

	public String getTotalHarga(){
		return totalHarga;
	}
}