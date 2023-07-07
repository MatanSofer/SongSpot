package com.example.myapplication.Spotify

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.MotionEvent
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProviders
import com.example.myapplication.MainScreenTabLayout.MainScreensActivity
import com.example.myapplication.Spotify.data.TrackModel
import com.example.myapplication.Spotify.state.GlobalState
import com.example.myapplication.Spotify.ui.SharedViewModel
import com.spotify.android.appremote.api.SpotifyAppRemote


class SpotifyMainActivity : AppCompatActivity() {
    private lateinit var state: GlobalState
    var token: String? = null
    private lateinit var sharedViewModel: SharedViewModel
    private var queueInitialized = false
    companion object {
        lateinit var spotifyAppRemote :SpotifyAppRemote

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(com.example.myapplication.R.layout.activity_spotify_main)

        token = intent.getStringExtra("token")
        sharedViewModel =
            ViewModelProviders.of(this).get(SharedViewModel::class.java)

        state = application as GlobalState
        state.ACCESS_TOKEN = token
        state.socket?.connect()
        state.spotifyAppRemote = spotifyAppRemote
        state.spotifyHeaders["Authorization"] = "Bearer $token"
        Log.d("Spotify:", "SpotifyMainActivity-onCreate() - token : " +state.ACCESS_TOKEN)
        state.myContext = this

        val intent = Intent(this, MainScreensActivity::class.java)
        startActivity(intent)
    }


    fun addToQueue(track: TrackModel){

        Log.d("Spotify:", "SpotifyMainActivity-addToQueue() - called " )
        sharedViewModel.addToQueue(track)
        Log.d("Spotify:", "SpotifyMainActivity -addToQueue - track name :"+track.name)
        Log.d("Spotify:", "SpotifyMainActivity -addToQueue - track id :"+track.id )
        Log.d("Spotify:", "SpotifyMainActivity -addToQueue - track uri :"+track.uri)
            Log.d("Spotify:", "SpotifyMainActivity-addToQueue() - queue isnt init" )
            state.spotifyAppRemote?.playerApi?.play(track.uri)
            queueInitialized = true

    }
    override fun dispatchTouchEvent(ev: MotionEvent?): Boolean {
        if (currentFocus != null) {
            val imm: InputMethodManager = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(currentFocus!!.windowToken, 0)
        }
        return super.dispatchTouchEvent(ev)
    }

    override fun onStop() {
        Log.d("SpotifyMainActivity:", "onStop has been called - app remote disconnect")
        super.onStop()
       // SpotifyAppRemote.disconnect(state.spotifyAppRemote)
    }
}