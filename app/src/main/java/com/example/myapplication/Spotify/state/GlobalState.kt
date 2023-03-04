package com.example.myapplication.Spotify.state

import android.app.Application
import android.content.Context
import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.example.myapplication.Spotify.data.TrackModel
import com.example.myapplication.Spotify.network.BackendApi
import com.example.myapplication.Spotify.network.SpotifyWebApi
import com.github.nkzawa.socketio.client.IO
import com.github.nkzawa.socketio.client.Socket
import com.spotify.android.appremote.api.SpotifyAppRemote
import java.net.URISyntaxException

class GlobalState : Application() {
    var spotifyAppRemote: SpotifyAppRemote? = null
    var spotifyWebApi: SpotifyWebApi? = null
    var ACCESS_TOKEN: String? = null
    var spotifyHeaders: HashMap<String, String> = HashMap()
    var socket: Socket? = null
    var myContext: Context? = null
    val TAG = GlobalState::class.java.simpleName
    val searchResults1 = MutableLiveData<MutableList<TrackModel>>()

    override fun onCreate() {
        super.onCreate()
        spotifyHeaders["Accept"] = "application/json"
        spotifyHeaders["Content-Type"] = "application/json"
        try {
            socket = IO.socket(BackendApi.BASE_URL)
        } catch (e: URISyntaxException) {
            Log.e("Spotify:", "GlobalState - Couldn't initialize socket", e)
        }
    }

    override fun onTerminate() {
        super.onTerminate()
        if(spotifyAppRemote != null){
            spotifyAppRemote?.let {
                SpotifyAppRemote.disconnect(it)
            }
        }
    }

    fun getAppContext(): Context? {
        return myContext
    }




}