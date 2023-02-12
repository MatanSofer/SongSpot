package com.example.myapplication.Spotify.network

import com.example.myapplication.Spotify.data.SearchResults
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.HeaderMap
import retrofit2.http.Query

interface SpotifyWebApi{

    companion object{
        const val BASE_URL: String = "https://api.spotify.com/v1/"
    }

    @GET("search")
    fun search(@HeaderMap headers: Map<String, String>,
               @Query("q") query: String,
               @Query("type") type: String): Call<SearchResults>


}