package com.example.ytaudio.main

import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ytaudio.database.entities.AudioInfo
import com.example.ytaudio.repositories.AudioRepository
import com.example.ytaudio.service.AudioServiceConnection
import com.example.ytaudio.utils.extensions.id
import com.example.ytaudio.utils.extensions.isPlayEnabled
import com.example.ytaudio.utils.extensions.isPlaying
import com.example.ytaudio.utils.extensions.isPrepared
import kotlinx.coroutines.launch
import javax.inject.Inject

class MainActivityViewModel @Inject constructor(
    private val repository: AudioRepository,
    private val audioServiceConnection: AudioServiceConnection
) : ViewModel() {

    private val needUpdateObserver = Observer<List<AudioInfo>?> { list ->
        list?.filter { it.needUpdate }?.let {
            if (it.isNotEmpty()) {
                updateAudioInfo(it)
            }
        }
    }

    private fun updateAudioInfo(audioList: List<AudioInfo>) {
        viewModelScope.launch {
            repository.updateAudioInfoList(audioList)
        }
    }

    private val databaseAudioInfo = repository.audioInfoList.apply {
        observeForever(needUpdateObserver)
    }

    fun audioItemClicked(audioId: String) {
        playAudio(audioId, false)
    }

    private fun playAudio(audioId: String, pauseAllowed: Boolean) {
        val nowPlaying = audioServiceConnection.nowPlaying.value
        val transportControls = audioServiceConnection.transportControls

        val isPrepared = audioServiceConnection.playbackState.value?.isPrepared ?: false

        if (isPrepared && audioId == nowPlaying?.id) {
            audioServiceConnection.playbackState.value?.let {
                when {
                    it.isPlaying -> if (pauseAllowed) transportControls.pause() else Unit
                    it.isPlayEnabled -> transportControls.play()
                }
            }
        } else {
            transportControls.playFromMediaId(audioId, null)
        }
    }

    fun playAudio(audioId: String) {
        val nowPlaying = audioServiceConnection.nowPlaying.value
        val transportControls = audioServiceConnection.transportControls

        val isPrepared = audioServiceConnection.playbackState.value?.isPrepared ?: false

        if (isPrepared && audioId == nowPlaying?.id) {
            audioServiceConnection.playbackState.value?.let {
                when {
                    it.isPlaying -> transportControls.pause()
                    it.isPlayEnabled -> transportControls.play()
                }
            }
        } else {
            transportControls.playFromMediaId(audioId, null)
        }
    }

    override fun onCleared() {
        databaseAudioInfo.removeObserver(needUpdateObserver)
        super.onCleared()
    }
}