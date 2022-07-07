package com.example.submission3.injection

import android.app.Application
import com.example.submission3.data.UserRepository
import com.example.submission3.data.source.local.LocalDataSource
import com.example.submission3.data.source.remote.RemoteDataSource
import com.example.submission3.ui.detail.followers.FollowersViewModel
import com.example.submission3.ui.detail.following.FollowingViewModel
import com.example.submission3.ui.favorite.FavoriteViewModel
import com.example.submission3.ui.home.HomeViewModel
import com.example.submission3.ui.search.SearchViewModel
import com.example.submission3.utils.SettingPreference
import com.example.submission3.utils.ViewModelFactory
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.androidXModule
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.provider
import org.kodein.di.generic.singleton


class Injection : Application(), KodeinAware {

    override val kodein: Kodein = Kodein.lazy {
        import(androidXModule(this@Injection))

        bind() from this.provider { RemoteDataSource() }

        bind() from this.provider { applicationContext }

        bind<UserRepository>() with this.provider {
            UserRepository(this.instance(), this.instance()) }

        bind() from this.provider { LocalDataSource(this.instance()) }

        bind() from this.provider { FollowersViewModel(this.instance()) }

        bind() from this.provider { FollowingViewModel(this.instance()) }

        bind() from this.provider { SearchViewModel(this.instance(), this.instance()) }

        bind() from this.provider { FavoriteViewModel(this.instance()) }

        bind() from this.provider { SettingPreference(this.instance()) }

        bind() from this.provider { HomeViewModel(this.instance(), this.instance()) }

        bind() from this.singleton { ViewModelFactory(this.instance(), this.instance()) }

    }


}