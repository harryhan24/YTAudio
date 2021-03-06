package com.example.ytaudio.data.videodata


interface YTData {
    val id: String
    val details: Details
    val thumbnails: List<Thumbnail>
    val audioStreams: List<AudioStream>
    val aliveTimeMillis: Long
    val lastUpdateTimeMillis: Long
}