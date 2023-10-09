package dev.haqim.myrawg.data.remote.response

import com.google.gson.annotations.SerializedName

data class RatingsItemResponse(

	@field:SerializedName("count")
	val count: Int,

	@field:SerializedName("id")
	val id: Int,

	@field:SerializedName("title")
	val title: String,

	@field:SerializedName("percent")
	val percent: Double
)