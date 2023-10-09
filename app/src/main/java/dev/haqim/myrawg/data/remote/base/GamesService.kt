package dev.haqim.myrawg.data.remote.base

import dev.haqim.myrawg.data.remote.response.GameDetailResponse
import dev.haqim.myrawg.data.remote.response.GamesResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface GamesService {
    @GET("games")
    suspend fun getGames(
//        @Query("key") key: String,
        @Query("search") search: String?,
        @Query("page") page: Int,
        @Query("page_size") pageSize: Int
    ): GamesResponse

    @GET("games/{id}")
    suspend fun getGame(
        @Path("id") id: Int,
//        @Query("key") key: String,
    ): GameDetailResponse
    
    
}