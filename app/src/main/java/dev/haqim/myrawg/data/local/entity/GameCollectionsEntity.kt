package dev.haqim.myrawg.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import dev.haqim.myrawg.domain.model.GameDetail
import dev.haqim.myrawg.domain.model.GamesListItem
import java.time.LocalDateTime

const val GAMES_COLLECTION_TABLE = "game_collection"
@Entity(GAMES_COLLECTION_TABLE)
data class GamesCollectionEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val gameId: Int,
    val title: String,
    val image: String,
    val rating: Int,
    val parentPlatforms: String, // Strings separated by comma
    val genres: String, // Strings separated by comma
    val lastUpdated: String
){
    fun toModel(): GamesListItem{
        return GamesListItem(
            gameId,
            genres = this.genres.split(","),
            image = this.image,
            parentPlatforms = this.parentPlatforms.split(","),
            rating = this.rating,
            title = this.title
        )
    }
}

fun List<GamesCollectionEntity>.toModel() = this.map { it.toModel() }

fun GameDetail.toGameCollectionEntity() = GamesCollectionEntity(
    gameId = this.id,
    title = this.title,
    image = this.image,
    rating = this.ratingTop,
    parentPlatforms = this.parentPlatforms.joinToString(","),
    genres = this.genres.joinToString(","),
    lastUpdated = this.lastUpdate
)