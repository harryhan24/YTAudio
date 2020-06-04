package com.example.ytaudio.screens.playlist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.ytaudio.R
import com.example.ytaudio.database.AudioDatabase
import com.example.ytaudio.databinding.PlaylistFragmentBinding

class PlaylistFragment : Fragment() {

    private lateinit var binding: PlaylistFragmentBinding
    private lateinit var viewModel: PlaylistViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding =
            DataBindingUtil.inflate(inflater, R.layout.playlist_fragment, container, false)

        val application = requireNotNull(this.activity).application
        val dataSource = AudioDatabase.getInstance(application).audioDatabaseDao
        val viewModelFactory = PlaylistViewModelFactory(dataSource, application)
        viewModel =
            ViewModelProvider(this, viewModelFactory).get(PlaylistViewModel::class.java)

        val adapter = PlaylistAdapter(AudioInfoListener {
            findNavController().navigate(
                PlaylistFragmentDirections.actionPlaylistFragmentToAudioPlayerFragment(it)
            )
        })

        binding.apply {
            playlistView.layoutManager =
                LinearLayoutManager(this@PlaylistFragment.context, RecyclerView.VERTICAL, false)

            playlistView.adapter = adapter

            viewModel = viewModel

            toolbar.setOnMenuItemClickListener {
                when (it.itemId) {
                    R.id.url_link -> {
                        this@PlaylistFragment.viewModel.onExtract(linkText.text.toString())
                        true
                    }
                    else -> false
                }
            }

            lifecycleOwner = this@PlaylistFragment
        }

        viewModel.audioPlaylist.observe(viewLifecycleOwner, Observer {
            it?.let {
                adapter.submitList(it)
            }
        })

        return binding.root
    }
}

