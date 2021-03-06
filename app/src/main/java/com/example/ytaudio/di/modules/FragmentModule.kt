package com.example.ytaudio.di.modules

import com.example.ytaudio.ui.fragments.*
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class FragmentModule {

    @ContributesAndroidInjector
    abstract fun contributePlaylistFragment(): PlaylistFragment

    @ContributesAndroidInjector
    abstract fun contributeSearchFragment(): SearchFragment

    @ContributesAndroidInjector
    abstract fun contributePlayerFragment(): PlayerFragment

    @ContributesAndroidInjector
    abstract fun contributeYouTubeFragment(): YouTubeFragment

    @ContributesAndroidInjector
    abstract fun contributeSearchResultsFragment(): SearchResultsFragment
}