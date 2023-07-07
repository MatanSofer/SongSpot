package com.example.myapplication.Spotify.ui.dashboard

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.RecycledViewPool
import com.example.myapplication.DataSingelton
import com.example.myapplication.MapsAPI.LocationGenre
import com.example.myapplication.R
import com.example.myapplication.Ranking.Ranking
import com.example.myapplication.Spotify.SpotifyMainActivity
import com.example.myapplication.Spotify.adapter.SearchResultAdapter
import com.example.myapplication.Spotify.state.GlobalState
import com.example.myapplication.Spotify.touch.ListRecyclerTouchCallback
import com.example.myapplication.Spotify.ui.SharedViewModel


class DashboardFragment : Fragment() {

    private lateinit var state: GlobalState
    private lateinit var sharedViewModel: SharedViewModel
    private val TAG = DashboardFragment::class.java.simpleName
    private lateinit var searchResultAdapter: SearchResultAdapter
    private lateinit var rvSearchResults: RecyclerView
    private var myContext: FragmentActivity? = null
    companion object {
        var queryType: Int = 0
    }

    interface PlaylistRecommendationHandler{
        fun getPlaylistRecommendations(seed: String)
    }
    private lateinit var recommendationHandler: PlaylistRecommendationHandler

    @SuppressLint("SuspiciousIndentation")
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_dashboard, container, false)
        sharedViewModel = activity?.run {
                ViewModelProviders.of(this)[SharedViewModel::class.java]
            } ?: throw Exception("Invalid Activity")

        Log.d("Spotify:", "DashboardFragment-onCreateView() - called")
        rvSearchResults = root.findViewById(R.id.rvSearchResults)
        state = activity?.application as GlobalState

        if (queryType == 1 ){
            recommendationHandler.getPlaylistRecommendations(DataSingelton.getInstance().getUserChosenPlace())
            sharedViewModel.performSearch(DataSingelton.getInstance().getUserChosenPlace(),Ranking.songsIdResults);
            initRecyclerView()
        }else{

            val randomGenre = LocationGenre.getRandomGenreForLocation(DataSingelton.getInstance().getUserChosenPlace())
            Log.d("Spotify:", "genre selected " + randomGenre);
            sharedViewModel.performSearch("genre:$randomGenre",Ranking.songsIdResults);
            initRecyclerView()
        }


        return root
    }

    override fun onAttach(context: Context) {
        state = activity?.application as GlobalState
        Log.d("Spotify:", "DashboardFragment-onAttach() - called")
        if (state.myContext != null) {
            super.onAttach(state.myContext!!)
            Log.d("Spotify:", "DashboardFragment-onAttach() - context isnt null")
        }
        Log.d("Spotify:", "DashboardFragment-onAttach() - context  null")
        this.myContext = state.myContext as SpotifyMainActivity
    }

    private fun initRecyclerView() {
        Log.d("Spotify:", "DashboardFragment-initRecyclerView() - called")
        searchResultAdapter = SearchResultAdapter(myContext, state.spotifyAppRemote!!)
        rvSearchResults.adapter = searchResultAdapter
        rvSearchResults.layoutManager = LinearLayoutManager(activity)
        val touchCallbackList = ListRecyclerTouchCallback(searchResultAdapter)
        val itemTouchHelper = ItemTouchHelper(touchCallbackList)
        itemTouchHelper.attachToRecyclerView(rvSearchResults)

        val itemDecoration = DividerItemDecoration(context, DividerItemDecoration.VERTICAL)

        rvSearchResults.addItemDecoration(itemDecoration)

        sharedViewModel.searchResult.observe(viewLifecycleOwner, Observer {
            searchResultAdapter.searchResults = it
            searchResultAdapter.notifyDataSetChanged()
        })
    }


}