package com.example.myapplication.Spotify.controllers
import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.example.myapplication.DataSingelton
import com.example.myapplication.MainScreenTabLayout.MainScreensActivity
import com.example.myapplication.Spotify.data.RecommendationResults
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
    var playlistTracks : MutableList<TrackModel> = mutableListOf()
    val playlistTracksId: MutableList<String> = mutableListOf()
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

    fun clearAllData() {
        playlist.value?.clear()
        searchResults.value?.clear()
        playlistTracks.clear()
        playlistTracksId.clear()
    }
    fun performSongById(idList: MutableList<String>){
        clearAllData()
        idList.forEach { id ->
            val track = application.spotifyWebApi?.getTrackById(application.spotifyHeaders, id)
            track?.enqueue(object:Callback<TrackModel> {
                override fun onResponse(call: Call<TrackModel>, response: Response<TrackModel>) {
                    var result = response.body()
                    result?.popularity = 10 // to flag this song is recomended
                    if(result?.id !in playlistTracksId){
                        playlistTracks.add(result as TrackModel)
                        result.id?.let { playlistTracksId.add(it) }
                        Log.d("playlistTracks performSongById size", playlistTracks.size.toString()+"---"+ result.id)
                    }
                    Log.d(
                        "Spotify:",
                        "Repositoty - performSongById() - result: " + result
                    )
                    searchResults.value=playlistTracks
                     Log.d("Spotify:", "Repository-performSongById() - responseList.value size is " + searchResults.value!!.size)

                }
                override fun onFailure(call: Call<TrackModel>, t: Throwable) {
                    TODO("Not yet implemented")
                }
            })
        }

    }
    fun performSearch (query: String, offSet: Int){
        var searchTracksList : MutableList<TrackModel> = mutableListOf()
        val searchTracks = application.spotifyWebApi?.search(application.spotifyHeaders, query, offSet,"track")
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
                searchTracksList = tracks as MutableList<TrackModel>
                Log.d(
                    "Spotify:",
                    "Repositoty - performSearch() - results: " + results
                )
                Log.d(
                    "Spotify:",
                    "Repositoty - performSearch() - tracks: " + tracks
                )

                searchTracksList.forEach { item ->
                    if(item.id !in playlistTracksId ){
                        playlistTracks.add(item)
                        item.id?.let { playlistTracksId.add(it) }
                        Log.d("playlistTracks search size", playlistTracks.size.toString()+"---"+item.id)
                    }
                }

                searchResults.value = playlistTracks
                Log.d("Spotify:", "Repository-performSearch() - searchResults.value size is " + searchResults.value!!.size)
                //set the number basge at view pager to number of songs
                MainScreensActivity.tabLayout.getTabAt(1)!!.orCreateBadge.number = searchResults.value!!.size
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

    fun performSearchPlaylist(query: String, offSet: Int) {
        var searchTracksList : MutableList<TrackModel> = mutableListOf()
        val playlistTrack = application.spotifyWebApi?.getPlaylistRecommendations(application.spotifyHeaders, query)

        playlistTrack?.enqueue(object : Callback<RecommendationResults> {
            override fun onFailure(call: Call<RecommendationResults>, t: Throwable) {
                //  Log.e(TAG, "Couldn't perform search request", t)
            }

            override fun onResponse(call: Call<RecommendationResults>, response: Response<RecommendationResults>) {
                val results = response.body()
                searchTracksList.forEach { item ->
                    if(item.id !in playlistTracksId ){
                        playlistTracks.add(item)
                        item.id?.let { playlistTracksId.add(it) }
                        Log.d("playlistTracks search size", playlistTracks.size.toString()+"---"+item.id)
                    }
                }

                searchResults.value = playlistTracks
                Log.d("Spotify:", "Repository-performSearch() - searchResults.value size is " + searchResults.value!!.size)
                //set the number basge at view pager to number of songs
                MainScreensActivity.tabLayout.getTabAt(1)!!.orCreateBadge.number = searchResults.value!!.size
            }
        })
    }

}