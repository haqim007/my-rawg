package dev.haqim.myrawg.data.remote.response

import com.google.gson.annotations.SerializedName

data class StoresItemResponse(

	@field:SerializedName("id")
	val id: Int,

	@field:SerializedName("store")
	val store: StoreResponse
)