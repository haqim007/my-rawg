package dev.haqim.myrawg.data.remote.response

import com.google.gson.annotations.SerializedName

data class ParentPlatformsItemResponse(

	@field:SerializedName("platform")
	val platform: PlatformResponse
)