package dev.haqim.myrawg.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import androidx.room.Relation
import dev.haqim.myrawg.data.util.convertDate
import dev.haqim.myrawg.domain.model.GameDetail

const val GAME_DETAIL_TABLE = "game_detail"
@Entity(GAME_DETAIL_TABLE)
data class GameDetailEntity(
    @PrimaryKey
    val id: Int,
    val title: String,
    val image: String,
    val lastUpdate: String,
    val ratingTop: Int,
    val topRatingsName: String,
    val totalRatingsVote: Int,
    val description: String,
    val parentPlatforms: String, //Strings separated by comma
    val platforms: String, //Strings separated by comma
    val metascore: Int,
    val genres: String, //Strings separated by comma,
    val releaseDate: String,
    val developers: String, // Strings separated by comma
    val publishers: String, // Strings separated by comma
){
    fun toModel(): GameDetail{
        return GameDetail(
            id = id,
            title,
            image,
            lastUpdate = convertDate(this.lastUpdate),
            ratingTop,
            topRatingsName,
            totalRatingsVote,
            description,
            this.parentPlatforms.split(","),
            this.platforms.split(","),
            metascore,
            genres.split(","),
            convertDate(this.releaseDate, format = "yyyy-MM-dd"),
            developers.split(","),
            publishers.split(",")
        )
    }
}

data class GameDetailWithCollectionStatusEntity(
    @Embedded val gameDetail: GameDetailEntity,
    @Relation(
        parentColumn = "id",
        entityColumn = "gameId"
    )
    val gameCollection: GamesCollectionEntity? = null
){
    val isCollected: Boolean get() = gameCollection != null
    fun toModel(): GameDetail{
        val detail = this.gameDetail
        return GameDetail(
            detail.id,
            detail.title,
            detail.image,
            lastUpdate = convertDate(detail.lastUpdate),
            detail.ratingTop,
            detail.topRatingsName,
            detail.totalRatingsVote,
            detail.description,
            detail.parentPlatforms.split(","),
            detail.platforms.split(","),
            detail.metascore,
            detail.genres.split(","),
            convertDate(detail.releaseDate, format = "yyyy-MM-dd"),
            detail.developers.split(","),
            detail.publishers.split(","),
            isCollected
        )
    }
}
