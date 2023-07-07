package com.example.myapplication.Spotify.ui

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import com.example.myapplication.DataSingelton
import com.example.myapplication.Spotify.controllers.Repository
import com.example.myapplication.Spotify.data.TrackModel
import com.example.myapplication.Spotify.state.GlobalState
import com.example.myapplication.Spotify.ui.dashboard.DashboardFragment

class SharedViewModel(application: Application) : AndroidViewModel(application){
    private val repository = Repository(application as GlobalState)
    private val TAG = SharedViewModel::class.java.simpleName
    val searchResult = repository.searchResults

    fun addToQueue(track: TrackModel){
        repository.addToQueue(track)
    }

    fun performSearch(query: String, songId: MutableList<String>){
        if(DashboardFragment.queryType == 1){
            repository.performSearchPlaylist(query, DataSingelton.getInstance().generateOffset())

        }else{
            repository.performSearch(query, DataSingelton.getInstance().generateOffset())
            repository.performSongById(songId)
        }

    }

}