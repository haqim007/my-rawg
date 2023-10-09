package dev.haqim.myrawg.data.remote.response

import com.google.gson.annotations.SerializedName
import dev.haqim.myrawg.data.local.entity.GameDetailEntity

data class GameDetailResponse(

	@field:SerializedName("added")
	val added: Int,

	@field:SerializedName("developers")
	val developers: List<DevelopersItemResponse>,

	@field:SerializedName("name_original")
	val nameOriginal: String,

	@field:SerializedName("rating")
	val rating: Double,

	@field:SerializedName("game_series_count")
	val gameSeriesCount: Int,

	@field:SerializedName("playtime")
	val playtime: Int,

	@field:SerializedName("platforms")
	val platforms: List<PlatformsItemResponse>,

	@field:SerializedName("rating_top")
	val ratingTop: Int,

	@field:SerializedName("reviews_text_count")
	val reviewsTextCount: Int,

	@field:SerializedName("publishers")
	val publishers: List<PublishersItemResponse>,

	@field:SerializedName("achievements_count")
	val achievementsCount: Int,

	@field:SerializedName("id")
	val id: Int,

	@field:SerializedName("parent_platforms")
	val parentPlatforms: List<ParentPlatformsItemResponse>,

	@field:SerializedName("reddit_name")
	val redditName: String,

	@field:SerializedName("ratings_count")
	val ratingsCount: Int,

	@field:SerializedName("slug")
	val slug: String,

	@field:SerializedName("released")
	val released: String,

	@field:SerializedName("youtube_count")
	val youtubeCount: Int,

	@field:SerializedName("movies_count")
	val moviesCount: Int,

	@field:SerializedName("description_raw")
	val descriptionRaw: String,

	@field:SerializedName("tags")
	val tags: List<TagsItemResponse>,

	@field:SerializedName("background_image")
	val backgroundImage: String,

	@field:SerializedName("tba")
	val tba: Boolean,

	@field:SerializedName("dominant_color")
	val dominantColor: String,

	@field:SerializedName("name")
	val name: String,

	@field:SerializedName("reddit_description")
	val redditDescription: String,

	@field:SerializedName("reddit_logo")
	val redditLogo: String,

	@field:SerializedName("updated")
	val updated: String,

	@field:SerializedName("reviews_count")
	val reviewsCount: Int,

	@field:SerializedName("metacritic")
	val metacritic: Int,

	@field:SerializedName("description")
	val description: String,

	@field:SerializedName("metacritic_url")
	val metacriticUrl: String,

	@field:SerializedName("parents_count")
	val parentsCount: Int,
	
	@field:SerializedName("creators_count")
	val creatorsCount: Int,

	@field:SerializedName("ratings")
	val ratings: List<RatingsItemResponse>,

	@field:SerializedName("genres")
	val genres: List<GenresItemResponse>,

	@field:SerializedName("saturated_color")
	val saturatedColor: String,

	@field:SerializedName("added_by_status")
	val addedByStatus: AddedByStatusResponse,

	@field:SerializedName("reddit_url")
	val redditUrl: String,

	@field:SerializedName("reddit_count")
	val redditCount: Int,

	@field:SerializedName("parent_achievements_count")
	val parentAchievementsCount: Int,

	@field:SerializedName("website")
	val website: String,

	@field:SerializedName("suggestions_count")
	val suggestionsCount: Int,

	@field:SerializedName("stores")
	val stores: List<StoresItemResponse>,

	@field:SerializedName("additions_count")
	val additionsCount: Int,

	@field:SerializedName("twitch_count")
	val twitchCount: Int,

	@field:SerializedName("background_image_additional")
	val backgroundImageAdditional: String,

	@field:SerializedName("esrb_rating")
	val esrbRating: EsrbRatingResponse,

	@field:SerializedName("screenshots_count")
	val screenshotsCount: Int,

	@field:SerializedName("reactions")
	val reactions: ReactionsResponse,

){
	fun toEntity(): GameDetailEntity{
		return GameDetailEntity(
			id = this.id,
			title = this.name,
			image = this.backgroundImage,
			lastUpdate = this.updated,
			ratingTop = this.ratingTop,
			topRatingsName = this.ratings.maxByOrNull { it.id }!!.title,
			totalRatingsVote = this.ratingsCount,
			description = this.descriptionRaw,
			parentPlatforms = this.parentPlatforms.joinToString(","){ it.platform.name },
			platforms = this.platforms.joinToString(","){ it.platform.name },
			metascore = this.metacritic,
			genres = this.genres.joinToString(","){ it.name },
			releaseDate = this.released,
			developers = this.developers.joinToString(",") { it.name },
			publishers = this.publishers.joinToString(","){ it.name }
		)
	}
}

data class PublishersItemResponse(

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

data class ReactionsResponse(

	@field:SerializedName("11")
	val jsonMember11: Int,

	@field:SerializedName("12")
	val jsonMember12: Int,

	@field:SerializedName("14")
	val jsonMember14: Int,

	@field:SerializedName("15")
	val jsonMember15: Int,

	@field:SerializedName("16")
	val jsonMember16: Int,

	@field:SerializedName("17")
	val jsonMember17: Int,

	@field:SerializedName("18")
	val jsonMember18: Int,

	@field:SerializedName("1")
	val jsonMember1: Int,

	@field:SerializedName("3")
	val jsonMember3: Int,

	@field:SerializedName("4")
	val jsonMember4: Int,

	@field:SerializedName("6")
	val jsonMember6: Int,

	@field:SerializedName("7")
	val jsonMember7: Int,

	@field:SerializedName("8")
	val jsonMember8: Int,

	@field:SerializedName("9")
	val jsonMember9: Int,

	@field:SerializedName("20")
	val jsonMember20: Int,

	@field:SerializedName("10")
	val jsonMember10: Int,

	@field:SerializedName("21")
	val jsonMember21: Int
)

data class DevelopersItemResponse(

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