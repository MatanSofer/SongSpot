package com.example.myapplication.Spotify.ui

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import com.example.myapplication.Spotify.controllers.Repository
import com.example.myapplication.Spotify.data.TrackModel
import com.example.myapplication.Spotify.state.GlobalState

class SharedViewModel(application: Application) : AndroidViewModel(application){
    private val repository = Repository(application as GlobalState)
    private val TAG = SharedViewModel::class.java.simpleName
    val searchResult = repository.searchResults
    val playlist = repository.playlist

    fun addToQueue(track: TrackModel){
        repository.addToQueue(track)
    }

    fun performSearch(query: String){
        repository.performSearch(query)
    }

    fun removeFromQueue(position: Int){
        Log.d(TAG, "Removed at $position")
        repository.removeFromQueue(position)
    }

    fun removeFromSearchResults(position: Int){
        repository.removeFromSearchResults(position)
    }

    fun removeAllFromSearchResults(){
        repository.removeAllFromSearchResults()
    }

    fun removeAllFromQueue(){
        repository.removeAllFromQueue()
    }
}