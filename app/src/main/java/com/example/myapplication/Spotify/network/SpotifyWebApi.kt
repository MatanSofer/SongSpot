package com.example.myapplication.Spotify.network

import com.example.myapplication.Spotify.data.RecommendationResults
import com.example.myapplication.Spotify.data.SearchResults
import com.example.myapplication.Spotify.data.TrackModel
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.HeaderMap
import retrofit2.http.Path
import retrofit2.http.Query

interface SpotifyWebApi{

    companion object{
        const val BASE_URL: String = "https://api.spotify.com/v1/"
    }

    @GET("playlistRecommendations")
    fun getPlaylistRecommendations(
        @HeaderMap headers: Map<String, String>,
        @Query("seed") seed: String): Call<RecommendationResults>
    @GET("search")
    fun search(@HeaderMap headers: Map<String, String>,
               @Query("q") query: String,
               @Query("offset") offset: Int,
               @Query("type") type: String): Call<SearchResults>

    @GET("tracks/{id}")
    fun getTrackById(@HeaderMap headers: Map<String, String>,
                     @Path("id") trackId: String): Call<TrackModel>
}