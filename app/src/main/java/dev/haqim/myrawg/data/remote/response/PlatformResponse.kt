package dev.haqim.myrawg.data.remote.response

import com.google.gson.annotations.SerializedName

data class PlatformResponse(

	@field:SerializedName("name")
	val name: String,

	@field:SerializedName("id")
	val id: Int,

	@field:SerializedName("slug")
	val slug: String
)