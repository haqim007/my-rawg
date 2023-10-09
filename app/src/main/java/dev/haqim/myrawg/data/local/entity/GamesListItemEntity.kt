package dev.haqim.myrawg.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import dev.haqim.myrawg.domain.model.GamesListItem

const val GAME_LIST_ITEM_TABLE = "game_list_item"
@Entity(GAME_LIST_ITEM_TABLE)
data class GamesListItemEntity(
    @PrimaryKey
    val id: Int,
    val title: String,
    val image: String,
    val rating: Int,
    val parentPlatforms: String, // Strings separated by comma
    val genres: String, // Strings separated by comma,
    val lastUpdated: String,
    val addedToPublicCollection: Int
){
    fun toModel(): GamesListItem {
        return GamesListItem(
            id,
            genres = this.genres.split(","),
            image = this.image,
            parentPlatforms = this.parentPlatforms.split(","),
            rating = this.rating,
            title = this.title
        )
    }
}

fun List<GamesListItemEntity>.toModel() = this.map { it.toModel() }
