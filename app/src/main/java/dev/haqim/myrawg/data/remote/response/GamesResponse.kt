package dev.haqim.myrawg.data.remote.response

import com.google.gson.annotations.SerializedName
import dev.haqim.myrawg.data.local.entity.GamesListItemEntity

data class GamesResponse(

	@field:SerializedName("count")
	val count: Int,

	@field:SerializedName("next")
	val nextUrl: String?,

	@field:SerializedName("previous")
	val previousUrl: String?,

	@field:SerializedName("results")
	val results: List<GamesItemResponse>,
)

data class GamesItemResponse(

	@field:SerializedName("added")
	val added: Int,

	@field:SerializedName("rating")
	val rating: Double,

	@field:SerializedName("metacritic")
	val metaCritic: Int,

	@field:SerializedName("playtime")
	val playtime: Int,

	@field:SerializedName("short_screenshots")
	val shortScreenshots: List<PicturesItemResponse>,

	@field:SerializedName("platforms")
	val platforms: List<PlatformsItemResponse>,

	@field:SerializedName("rating_top")
	val ratingTop: Int,

	@field:SerializedName("reviews_text_count")
	val reviewsTextCount: Int,

	@field:SerializedName("ratings")
	val ratings: List<RatingsItemResponse>,

	@field:SerializedName("genres")
	val genres: List<GenresItemResponse>,

	@field:SerializedName("saturated_color")
	val saturatedColor: String,

	@field:SerializedName("id")
	val id: Int,

	@field:SerializedName("added_by_status")
	val addedByStatus: AddedByStatusResponse,

	@field:SerializedName("parent_platforms")
	val parentPlatforms: List<ParentPlatformsItemResponse>,

	@field:SerializedName("ratings_count")
	val ratingsCount: Int,

	@field:SerializedName("slug")
	val slug: String,

	@field:SerializedName("released")
	val released: String,

	@field:SerializedName("suggestions_count")
	val suggestionsCount: Int,

	@field:SerializedName("stores")
	val stores: List<StoresItemResponse>,

	@field:SerializedName("tags")
	val tags: List<TagsItemResponse>,

	@field:SerializedName("background_image")
	val backgroundImage: String,

	@field:SerializedName("tba")
	val tba: Boolean,

	@field:SerializedName("dominant_color")
	val dominantColor: String,

	@field:SerializedName("esrb_rating")
	val esrbRating: EsrbRatingResponse,

	@field:SerializedName("name")
	val name: String,

	@field:SerializedName("updated")
	val updated: String,

	@field:SerializedName("reviews_count")
	val reviewsCount: Int
){
	fun toEntity(): GamesListItemEntity {
		return GamesListItemEntity(
			id = this.id,
			title = this.name,
			rating = this.ratingTop,
			image = this.backgroundImage,
			parentPlatforms = this.parentPlatforms.joinToString(separator = ",") { it.platform.slug },
			genres = this.genres.joinToString(separator = ",") { it.name },
			lastUpdated = this.updated,
			addedToPublicCollection = this.added
		)
	}
}

fun List<GamesItemResponse>.toEntity() = this.map { it.toEntity() }

data class StoreResponse(

	@field:SerializedName("games_count")
	val gamesCount: Int,

	@field:SerializedName("domain")
	val domain: String,

	@field:SerializedName("name")
	val name: String,

	@field:SerializedName("id")
	val id: Int,

	@field:SerializedName("image_background")
	val imageBackground: String,

	@field:SerializedName("slug")
	val slug: String
)

data class PicturesItemResponse(

	@field:SerializedName("image")
	val image: String,

	@field:SerializedName("id")
	val id: Int
)


