package com.example.myapplication.Spotify

import android.R
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.MotionEvent
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.NavController
import com.example.myapplication.MainScreenTabLayout.MainScreensActivity

import com.example.myapplication.Spotify.data.TrackModel
import com.example.myapplication.Spotify.state.GlobalState
import com.example.myapplication.Spotify.ui.SharedViewModel
import com.example.myapplication.Spotify.ui.dashboard.DashboardFragment
import com.spotify.android.appremote.api.SpotifyAppRemote


class SpotifyMainActivity : AppCompatActivity() {
    private lateinit var state: GlobalState
    var token: String? = null
    private lateinit var sharedViewModel: SharedViewModel
    private var queueInitialized = false
    //private var myContext: Context? = null
   // private lateinit var navController: NavController
    companion object {
        lateinit var spotifyAppRemote :SpotifyAppRemote;

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(com.example.myapplication.R.layout.activity_spotify_main)

        Log.d("Spotify:", "SpotifyMainActivity-onCreate() - called")
      //  val navView: BottomNavigationView = findViewById(R.id.nav_view)
        //playerApi = mSpotifyAppRemote.getPlayerApi();
        token = intent.getStringExtra("token")

        sharedViewModel =
            ViewModelProviders.of(this).get(SharedViewModel::class.java)

        state = application as GlobalState
        state.ACCESS_TOKEN = token
        state.socket?.connect()
        state.spotifyAppRemote = spotifyAppRemote;
        state.spotifyHeaders["Authorization"] = "Bearer $token"
        Log.d("Spotify:", "SpotifyMainActivity-onCreate() - token : " +state.ACCESS_TOKEN)
        state.myContext = this

//        val manager: FragmentManager = supportFragmentManager
//        val transaction: FragmentTransaction = manager.beginTransaction()
//        transaction.replace(com.example.myapplication.R.id.title_fragment, DashboardFragment()).commit()
        val intent = Intent(this, MainScreensActivity::class.java)
        startActivity(intent)
        

        ////////////

//        navController = findNavController(R.id.nav_host_fragment)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.

//        val appBarConfiguration = AppBarConfiguration(
//            setOf(
//                R.id.navigation_home, R.id.navigation_dashboard, R.id.navigation_notifications
//            )
//        )

       // setupActionBarWithNavController( navController, appBarConfiguration)
        //navView.setupWithNavController(navController)
        //////////////
//        state.spotifyAppRemote?.playerApi?.play("spotify:track:7t92X8XWksvUH8Hl0heDQ9")

    }


    fun addToQueue(track: TrackModel){

        Log.d("Spotify:", "SpotifyMainActivity-addToQueue() - called " )
        sharedViewModel.addToQueue(track)
        Log.d("Spotify:", "SpotifyMainActivity -addToQueue - track name :"+track.name);
        Log.d("Spotify:", "SpotifyMainActivity -addToQueue - track id :"+track.id );
        Log.d("Spotify:", "SpotifyMainActivity -addToQueue - track uri :"+track.uri);
      //  if(!queueInitialized){
            Log.d("Spotify:", "SpotifyMainActivity-addToQueue() - queue isnt init" )
            state.spotifyAppRemote?.playerApi?.play(track.uri)
            queueInitialized = true


//        } else {
//            Log.d("Spotify:", "SpotifyMainActivity-addToQueue() - queue init" )
//            val handler = Handler()
//            handler.postDelayed({
//                state.spotifyAppRemote?.playerApi?.queue(track.uri)
//            },
//                1000
//            )
//        }
    }
    override fun dispatchTouchEvent(ev: MotionEvent?): Boolean {
        //Log.d("Spotify:", "SpotifyMainActivity-dispatchTouchEvent() - called" )
        if (currentFocus != null) {
            val imm: InputMethodManager = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(currentFocus!!.windowToken, 0)
        }
        return super.dispatchTouchEvent(ev)
    }


    fun removeFromQueue(position: Int){
        Log.d("SpotifyMainActivity:", "Removed at $position")
        sharedViewModel.removeFromQueue(position)
    }

    fun removeAllFromQueue(){
        sharedViewModel.removeAllFromQueue()
    }

    fun removeFromSearchResults(position: Int){
        sharedViewModel.removeFromSearchResults(position)
    }

    fun removeAllFromSearchResults(){
        sharedViewModel.removeAllFromSearchResults()
    }

    override fun onStop() {
        Log.d("SpotifyMainActivity:", "onStop has been called - app remote disconnect")
        super.onStop()
       // SpotifyAppRemote.disconnect(state.spotifyAppRemote)
    }
}