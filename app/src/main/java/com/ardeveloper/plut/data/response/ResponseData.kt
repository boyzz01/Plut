package com.ardeveloper.plut.data.response

import com.google.gson.annotations.SerializedName

data class ResponseData(

	@field:SerializedName("data")
	val data: String? = null,

	@field:SerializedName("success")
	val success: Boolean? = null
)
