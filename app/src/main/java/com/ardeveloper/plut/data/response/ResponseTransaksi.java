package com.ardeveloper.plut.data.response;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class ResponseTransaksi{

	@SerializedName("item")
	private List<TransaksiItem> item;

	@SerializedName("transaksi")
	private Transaksi transaksi;

	public List<TransaksiItem> getItem(){
		return item;
	}

	public Transaksi getTransaksi(){
		return transaksi;
	}
}