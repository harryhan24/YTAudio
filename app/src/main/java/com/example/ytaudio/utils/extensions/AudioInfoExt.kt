package com.example.ytaudio.utils.extensions

import android.support.v4.media.MediaMetadataCompat
import com.example.ytaudio.data.audioinfo.AudioInfo
import com.example.ytaudio.vo.PlaylistItem


fun Iterable<AudioInfo>.toMediaMetadataList() =
    map { MediaMetadataCompat.Builder().from(it).build() }

fun Iterable<AudioInfo>.toPlaylistItemList() =
    map { PlaylistItem.from(it) }