package com.example.myapplication.Spotify.network

import com.example.myapplication.Spotify.data.RecommendationResults
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface BackendApi {

    companion object{
        const val BASE_URL: String = "https://spotify-colab.herokuapp.com"
    }

//    @GET("/rooms/create")
//    fun createRoom(
//        @Query("name") name: String): Call<RecommendationResults>
}