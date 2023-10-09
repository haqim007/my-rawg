package dev.haqim.myrawg.data.remote.response

import com.google.gson.annotations.SerializedName

data class GenresItemResponse(

	@field:SerializedName("games_count")
	val gamesCount: Int,

	@field:SerializedName("name")
	val name: String,

	@field:SerializedName("id")
	val id: Int,

	@field:SerializedName("image_background")
	val imageBackground: String,

	@field:SerializedName("slug")
	val slug: String
)