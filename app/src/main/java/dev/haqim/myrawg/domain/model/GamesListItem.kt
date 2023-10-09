package dev.haqim.myrawg.domain.model


data class GamesListItem(
    val id: Int,
    val title: String,
    val image: String,
    val rating: Int,
    val parentPlatforms: List<String>, //list of name of platforms parents
    val genres: List<String> // list of genres
)
