package com.example.myapplication.Spotify.adapter

import android.content.res.ColorStateList
import android.graphics.Color
import android.graphics.drawable.AnimationDrawable
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.View.INVISIBLE
import android.view.View.VISIBLE
import android.view.ViewGroup
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.example.myapplication.R
import com.example.myapplication.Ranking.Ranking
import com.example.myapplication.Spotify.SpotifyMainActivity
import com.example.myapplication.Spotify.data.TrackModel
import com.example.myapplication.Spotify.touch.ListItemTouchHelperCallback
import com.spotify.android.appremote.api.SpotifyAppRemote
import kotlinx.android.synthetic.main.search_result_track.view.*
import java.util.*


class SearchResultAdapter: RecyclerView.Adapter<SearchResultAdapter.ViewHolder>,
    ListItemTouchHelperCallback {

    var searchResults: MutableList<TrackModel> = mutableListOf()
    private val context: SpotifyMainActivity?
    private var spotifyAppRemote: SpotifyAppRemote
    private val TAG = SearchResultAdapter::class.java.simpleName
    private val clickedSongIds: MutableSet<String?> = mutableSetOf()
    constructor(context: FragmentActivity? , appRemote: SpotifyAppRemote){
        this.context = context as SpotifyMainActivity
        this.spotifyAppRemote = appRemote
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view =  LayoutInflater.from(context).inflate(
            R.layout.search_result_track, parent, false
        )
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return searchResults.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentTrack = searchResults[position]

        val images = currentTrack.album?.images

        Glide
            .with(context!!)
            .load(images?.get(0)?.url)
            .centerCrop()
            .placeholder(ColorDrawable(Color.BLACK))
            .listener(object: RequestListener<Drawable> {
                override fun onLoadFailed(
                    e: GlideException?,
                    model: Any?,
                    target: Target<Drawable>?,
                    isFirstResource: Boolean
                ): Boolean {
                    Log.e(TAG, "Problem Loading Image", e)
                    return false
                }

                override fun onResourceReady(
                    resource: Drawable?,
                    model: Any?,
                    target: Target<Drawable>?,
                    dataSource: DataSource?,
                    isFirstResource: Boolean
                ): Boolean {
                    return false
                }

            })
            .into(holder.ivTrackImage)

        //that means that the song is recommended by algorithm
        if(currentTrack.popularity == 10){
            currentTrack.id?.let { Log.d("currentTrack.popularity", it) }
            val flashingBorderView = holder.flashingBorderView.background
            if (flashingBorderView is AnimationDrawable) {
                flashingBorderView.start()
            }
        }else {
            val flashingBorderView = holder.flashingBorderView.background
            if (flashingBorderView is AnimationDrawable) {
                flashingBorderView.stop() // Stop the animation
                flashingBorderView.selectDrawable(0) // Reset the drawable to its initial frame
            }
        }
        if (!clickedSongIds.contains(currentTrack.id)) {
            holder.progressbar.visibility = INVISIBLE
            holder.ratingBar.rating = 0.0f
            holder.saveRating.setText("SAVE")
            holder.saveRating.setTextColor(Color.GRAY)
            val colorInt: Int = context.getColor(R.color.white)
            holder.saveRating.backgroundTintList = ColorStateList.valueOf(colorInt)
        }
        else{

            holder.saveRating.setText("RATING SAVED")
            holder.saveRating.setTextColor(Color.WHITE)
            val colorInt: Int = context.getColor(R.color.color8)
            holder.saveRating.backgroundTintList = ColorStateList.valueOf(colorInt)
        }
        holder.saveRating.setOnClickListener{view ->
            clickedSongIds.add(currentTrack.id)

            holder.saveRating.setText("RATING SAVED")
            holder.saveRating.setTextColor(Color.WHITE)
            val colorInt: Int = context.getColor(R.color.color8)
            holder.saveRating.backgroundTintList = ColorStateList.valueOf(colorInt)
            Log.d("Spotify:", "Search Result Adapter -holder - saved reting :"+holder.ratingBar.rating );
            Log.d("BigQueryActivity", "song id is "+currentTrack.id )
            Ranking.currentSongID = currentTrack.id
            Ranking.createSetQuery(holder.ratingBar.rating.toInt(),context,holder.progressbar)

        }

        holder.tvTrackName.text = currentTrack.name
        val artistNames = mutableListOf<String?>()
        currentTrack.artists?.forEach { artist ->
            artistNames.add(artist.name)
        }

        holder.tvArtists.text = artistNames.joinToString(separator = ", ")

        holder.searchResultCard.setOnClickListener {
            Log.d("Spotify:", "Search Result Adapter -holder - track clicked :"+currentTrack.id );
            context.addToQueue(currentTrack)

        }
    }


    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val ivTrackImage  = itemView.ivTrackImage
        val tvArtists = itemView.tvArtists
        val tvTrackName = itemView.tvTrackName
        val searchResultCard  = itemView.searchResultCard
        var ratingBar = itemView.ratingBar
        var saveRating = itemView.saveRating
        var progressbar = itemView.progressBar
        val flashingBorderView = itemView.findViewById<View>(R.id.flashing_border_view)

    }

    override fun onDismissed(position: Int) {
        TODO("Not yet implemented")
    }

    override fun onItemMoved(fromPosition: Int, toPosition: Int) {
        Log.d("Spotify:", "Search Result Adapter - onItemMoved item swiped" );
        Collections.swap(searchResults, fromPosition, toPosition)
        notifyItemMoved(fromPosition, toPosition)
    }


}