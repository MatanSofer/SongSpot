package com.example.myapplication.Spotify.network

interface BackendApi {

    companion object{
        const val BASE_URL: String = "https://spotify-colab.herokuapp.com"
    }

//    @GET("/rooms/create")
//    fun createRoom(
//        @Query("name") name: String): Call<RecommendationResults>
}