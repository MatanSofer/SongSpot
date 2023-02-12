package com.example.myapplication.Spotify.ui.home

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.Spotify.touch.ListRecyclerTouchCallback
//import com.example.myapplication.Spotify.adapter.PlaylistAdapter
import com.example.myapplication.R
import com.example.myapplication.Spotify.state.GlobalState
import com.example.myapplication.Spotify.ui.SharedViewModel
import com.spotify.protocol.types.Track

//class HomeFragment : Fragment() {
//
//    private lateinit var sharedViewModel: SharedViewModel
//    private lateinit var playlistAdapter: PlaylistAdapter
//    private lateinit var state: GlobalState
//    private lateinit var recyclerPlaylist: RecyclerView
//    private lateinit var layoutPlaylist: ConstraintLayout
//    private lateinit var layoutEmpty: ConstraintLayout
//    private lateinit var layoutProgress: ConstraintLayout
//    private val TAG: String = HomeFragment::class.java.simpleName
//
//    override fun onCreateView(
//        inflater: LayoutInflater,
//        container: ViewGroup?,
//        savedInstanceState: Bundle?
//    ): View? {
//        sharedViewModel = activity?.run {
//            ViewModelProviders.of(this)[SharedViewModel::class.java]
//        } ?: throw Exception("Invalid Activity")
//
//        val root = inflater.inflate(R.layout.fragment_home, container, false)
//        recyclerPlaylist = root.findViewById(R.id.recyclerPlaylist)
//        layoutEmpty = root.findViewById(R.id.layoutEmpty)
//        layoutPlaylist = root.findViewById(R.id.layoutPlaylist)
//        layoutProgress = root.findViewById(R.id.layoutProgress)
//        layoutProgress.visibility = View.INVISIBLE
//
//
//
//        state = activity?.application as GlobalState
//
//        initRecyclerView()
//        setPlaybackListener()
//        return root
//    }
//
//    private fun initRecyclerView() {
//
//        playlistAdapter = PlaylistAdapter(requireContext(), state.spotifyAppRemote!!)
//
//        recyclerPlaylist.adapter = playlistAdapter
//        recyclerPlaylist.layoutManager = LinearLayoutManager(activity)
//        val touchCallbackList = ListRecyclerTouchCallback(playlistAdapter)
//        val itemTouchHelper = ItemTouchHelper(touchCallbackList)
//        itemTouchHelper.attachToRecyclerView(recyclerPlaylist)
//
//        val itemDecoration = DividerItemDecoration(context, DividerItemDecoration.VERTICAL)
//
//        recyclerPlaylist.addItemDecoration(itemDecoration)
//
//        sharedViewModel.playlist.observe(viewLifecycleOwner, Observer {
//            playlistAdapter.playlist = it
//            playlistAdapter.notifyDataSetChanged()
//            if(it.size > 0){
//                layoutEmpty.visibility = View.INVISIBLE
//                layoutPlaylist.visibility = View.VISIBLE
//            } else {
//                layoutEmpty.visibility = View.VISIBLE
//                layoutPlaylist.visibility = View.INVISIBLE
//            }
//        })
//    }
//
//    private fun setPlaybackListener() {
//        state.spotifyAppRemote?.let {
//            // Subscribe to PlayerState
//            it.playerApi.subscribeToPlayerState().setEventCallback {
//                val track: Track = it.track
//                Log.d(TAG, track.name + " by " + track.artist.name)
//            }.setErrorCallback { throwable ->
//                Log.e(TAG, "Encountered Playback Error", throwable)
//            }
//        }
//    }
//
//
//}