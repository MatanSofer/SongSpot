package com.example.myapplication.Spotify.adapter

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target

import com.example.myapplication.Spotify.touch.ListItemTouchHelperCallback
import com.example.myapplication.R
import com.example.myapplication.Spotify.SpotifyMainActivity
import com.example.myapplication.Spotify.data.TrackModel
import com.spotify.android.appremote.api.SpotifyAppRemote
import kotlinx.android.synthetic.main.playlist_item.view.*

import java.util.*

//class PlaylistAdapter: RecyclerView.Adapter<PlaylistAdapter.ViewHolder>,
//    ListItemTouchHelperCallback {
//
//    var playlist: MutableList<TrackModel> = mutableListOf()
//    private val context: SpotifyMainActivity?
//    private val spotifyAppRemote: SpotifyAppRemote
//    private val TAG = PlaylistAdapter::class.java.simpleName
//
//    constructor(context: Context, appRemote: SpotifyAppRemote ){
//        this.context = context as SpotifyMainActivity
//        this.spotifyAppRemote = appRemote
//    }
//
//    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
//        val view =  LayoutInflater.from(context).inflate(
//            R.layout.playlist_item, parent, false
//        )
//
//        return ViewHolder(view)
//    }
//
//    override fun getItemCount(): Int {
//        return playlist.size
//    }
//
//    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
//        val currentTrack = playlist[position]
//        val images = currentTrack.album?.images
//
//        Glide
//            .with(context!!)
//            .load(images?.get(0)?.url)
//            .centerCrop()
//            .placeholder(ColorDrawable(Color.BLACK))
//            .listener(object: RequestListener<Drawable> {
//                override fun onLoadFailed(
//                    e: GlideException?,
//                    model: Any?,
//                    target: Target<Drawable>?,
//                    isFirstResource: Boolean
//                ): Boolean {
//                    Log.e(TAG, "Problem Loading Image", e)
//                    return false
//                }
//
//                override fun onResourceReady(
//                    resource: Drawable?,
//                    model: Any?,
//                    target: Target<Drawable>?,
//                    dataSource: DataSource?,
//                    isFirstResource: Boolean
//                ): Boolean {
//                    return false
//                }
//
//            })
//            .into(holder.ivTrackImage)
//
//
//        holder.tvTrackName.text = currentTrack.name
//        val artistNames = mutableListOf<String?>()
//        currentTrack.artists?.forEach { artist ->
//            artistNames.add(artist.name)
//        }
//
//        holder.tvArtists.text = artistNames.joinToString(separator = ", ")
//
//        holder.btnDelete.setOnClickListener {
//            context.removeFromQueue(position)
//        }
//    }
//
//
//    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
//        val ivTrackImage  = itemView.ivTrackImage
//        val tvArtists = itemView.tvArtists
//        val tvTrackName = itemView.tvTrackName
//        val btnDelete = itemView.btnDelete
//    }
//
//    override fun onDismissed(position: Int) {
//        context?.removeFromQueue(position)
//    }
//
//    override fun onItemMoved(fromPosition: Int, toPosition: Int) {
//        Collections.swap(playlist, fromPosition, toPosition)
//        notifyItemMoved(fromPosition, toPosition)
//    }


//}