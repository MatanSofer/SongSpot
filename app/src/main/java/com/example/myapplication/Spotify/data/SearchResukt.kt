package com.example.myapplication.Spotify.data

import java.io.Serializable

data class Album(val album_type: String?, val artists: List<Artist>?, val href: String?, val id: String?, val images: List<Image>?, val name: String?, val release_date: String?, val release_date_precision: String?, val total_tracks: Number?, val type: String?, val uri: String?) :
    Serializable

data class Artist(val href: String?, val id: String?, val name: String?, val type: String?, val uri: String?) :
    Serializable

data class SearchResults(val tracks: Tracks?) : Serializable

data class TrackModel(val album: Album?, val artists: List<Artist>?,
                      val available_markets: List<Any>?, val disc_number: Number?,
                      val duration_ms: Number?, val explicit: Boolean?,
                      val href: String?, val id: String?, val is_local: Boolean?,
                      val name: String?, val popularity: Number?, val preview_url: String?,
                      val track_number: Number?, val type: String?, val uri: String?): Serializable


data class Tracks(val href: String?, val items: List<TrackModel>?, val limit: Number?, val next: String?, val offset: Number?, val previous: Any?, val total: Number?) :
    Serializable

data class Image(val height: Number?, val url: String?, val width: Number?) : Serializable

data class RecommendationResults(val recommendations: List<Recommendation>?)

data class Recommendation(val count: Number, val track: TrackModel)