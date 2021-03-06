package com.example.ytaudio.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.DividerItemDecoration
import com.example.ytaudio.databinding.FragmentSearchResultsBinding
import com.example.ytaudio.di.Injectable
import com.example.ytaudio.ui.adapters.YTLoadStateAdapter
import com.example.ytaudio.ui.adapters.YouTubeItemsAdapter
import com.example.ytaudio.ui.viewmodels.SearchResultsViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject


class SearchResultsFragment : Fragment(), Injectable {

    @Inject
    lateinit var searchResultsViewModelFactory: ViewModelProvider.Factory

    private val searchResultsViewModel: SearchResultsViewModel by viewModels {
        searchResultsViewModelFactory
    }

    private lateinit var binding: FragmentSearchResultsBinding

    private val youtubeItemsAdapter = YouTubeItemsAdapter {
        Toast.makeText(context, it.videoId, Toast.LENGTH_SHORT).show()
    }

    private var job: Job? = null

    private val navArgs: SearchResultsFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSearchResultsBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.apply {
            recyclerView.adapter = youtubeItemsAdapter.withLoadStateFooter(YTLoadStateAdapter())
            recyclerView
                .addItemDecoration(DividerItemDecoration(activity, DividerItemDecoration.VERTICAL))
            searchToolbar.searchText.setText(navArgs.query)
            searchToolbar.toolbar.setNavigationOnClickListener {
                findNavController().navigate(SearchResultsFragmentDirections.actionSearchResultsFragmentToYouTubeFragment())
            }
            lifecycleOwner = this@SearchResultsFragment
        }
        setFromQuery()
    }

    private fun setFromQuery() {
        job?.cancel()
        job = lifecycleScope.launch {
            searchResultsViewModel.getFromQuery(navArgs.query).collectLatest {
                youtubeItemsAdapter.submitData(it)
            }
        }
    }
}