package dev.haqim.myrawg.domain.model

data class GameDetail(
    val id: Int,
    val title: String,
    val image: String,
    val lastUpdate: String,
    val ratingTop: Int,
    val topRatingsName: String,
    val totalRatingsVote: Int,
    val description: String,
    val parentPlatforms: List<String>,
    val platforms: List<String>, //list of name of platforms
    val metascore: Int,
    val genres: List<String>, //Strings separated by comma,
    val releaseDate: String,
    val developers: List<String>, // List of developer's name
    val publishers: List<String>, // List of publisher's name
    val isCollected: Boolean = false
)
