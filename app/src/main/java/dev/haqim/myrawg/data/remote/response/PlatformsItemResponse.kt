package dev.haqim.myrawg.data.remote.response

import com.google.gson.annotations.SerializedName

data class PlatformsItemResponse(

	@field:SerializedName("released_at")
	val releasedAt: String,

	@field:SerializedName("platform")
	val platform: PlatformResponse
)