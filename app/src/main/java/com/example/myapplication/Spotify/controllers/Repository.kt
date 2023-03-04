package com.example.myapplication.Spotify.controllers
import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.example.myapplication.BigQuery.GetBigQuery
import com.example.myapplication.MainScreenTabLayout.MainScreensActivity
import com.example.myapplication.Spotify.data.SearchResults
import com.example.myapplication.Spotify.data.TrackModel
import com.example.myapplication.Spotify.network.SpotifyWebApi
import com.example.myapplication.Spotify.state.GlobalState
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class Repository(private val application: GlobalState) {
    val playlist = MutableLiveData<MutableList<TrackModel>>()
    val searchResults = MutableLiveData<MutableList<TrackModel>>()


    init{
        val retrofit = Retrofit.Builder()
            .baseUrl(SpotifyWebApi.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        application.spotifyWebApi = retrofit.create(SpotifyWebApi::class.java)

        playlist.value = mutableListOf()
        searchResults.value = mutableListOf()
        application.searchResults1.value = mutableListOf()
    }

    fun performSearch (query: String){
        val searchTracks = application.spotifyWebApi?.search(application.spotifyHeaders, query, "track")
        Log.d(
            "Spotify:",
            "Repositoty - performSearch() - searchResult: " + searchTracks
        )
        searchTracks?.enqueue(object : Callback<SearchResults> {
            override fun onFailure(call: Call<SearchResults>, t: Throwable) {
              //  Log.e(TAG, "Couldn't perform search request", t)
            }

            override fun onResponse(call: Call<SearchResults>, response: Response<SearchResults>) {
                val results = response.body()
                val tracks = results?.tracks?.items
                Log.d(
                    "Spotify:",
                    "Repositoty - performSearch() - results: " + results
                )
                Log.d(
                    "Spotify:",
                    "Repositoty - performSearch() - tracks: " + tracks
                )
                // Add to search results
                searchResults.value = tracks as MutableList<TrackModel>
                application.searchResults1.value = tracks as MutableList<TrackModel>
                Log.d("Spotify:", "Repository-performSearch() - searchResults.value size is " + searchResults.value!!.size)
                //set the number basge at view pager to number of songs
                MainScreensActivity.tabLayout.getTabAt(2)!!.orCreateBadge.number = searchResults.value!!.size

            }
        })
    }

    fun addToQueue(track: TrackModel){

        val trackList = playlist.value
        trackList!!.add(track)
        playlist.value = trackList
       // Log.d(TAG, "Track queued")
    }

    fun removeFromQueue(position: Int){
        Log.d("Spotify:", "Repository-removeFromQueue - called")
        val trackList = playlist.value
        trackList!!.removeAt(position)
        playlist.value = trackList
    }

    fun removeFromSearchResults(position: Int){
        Log.d("Spotify:", "Repository-removeFromSearchResults() - called position is " +position)
        Log.d("Spotify:", "Repository-removeFromSearchResults() - searchResult size is " +searchResults.value!!.size)
        Log.d("Spotify:", "Repository-removeFromSearchResults() - searchResult size is " +application.searchResults1.value!!.size)
//        val trackList = searchResults.value
//        trackList!!.removeAt(position)
//        searchResults.value = trackList
//        searchResults.value = application.searchResults1.value
//        searchResults.value!!.removeAt(position)
        //searchResults.value!!.removeAt(position)
    }

    fun removeAllFromQueue(){
        val trackList = playlist.value
        trackList!!.clear()
        playlist.value = trackList
    }

    fun removeAllFromSearchResults(){
        val trackList = searchResults.value
        trackList!!.clear()
        searchResults.value = trackList
    }

}