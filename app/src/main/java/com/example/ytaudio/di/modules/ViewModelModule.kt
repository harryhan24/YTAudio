package com.example.ytaudio.di.modules

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.ytaudio.di.factories.ViewModelFactory
import com.example.ytaudio.di.keys.ViewModelKey
import com.example.ytaudio.ui.viewmodels.*
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap


@Module
abstract class ViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(MainActivityViewModel::class)
    abstract fun bindMainActivityViewModel(mainActivityViewModel: MainActivityViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(PlaylistViewModel::class)
    abstract fun bindPlaylistViewModel(playlistViewModel: PlaylistViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(PlayerViewModel::class)
    abstract fun bindPlayerViewModel(playerViewModel: PlayerViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(YouTubeViewModel::class)
    abstract fun bindYouTubeViewModel(youTubeViewModel: YouTubeViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(SearchViewModel::class)
    abstract fun bindSearchViewModel(searchViewModel: SearchViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(SearchResultsViewModel::class)
    abstract fun bindSearchResultsViewModel(searchResultsViewModel: SearchResultsViewModel): ViewModel

    @Binds
    abstract fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory
}