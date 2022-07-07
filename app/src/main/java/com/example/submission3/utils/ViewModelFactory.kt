package com.example.submission3.utils

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.submission3.data.UserRepository
import com.example.submission3.ui.detail.DetailViewModel
import com.example.submission3.ui.detail.followers.FollowersViewModel
import com.example.submission3.ui.detail.following.FollowingViewModel
import com.example.submission3.ui.favorite.FavoriteViewModel
import com.example.submission3.ui.home.HomeViewModel
import com.example.submission3.ui.search.SearchViewModel
import com.example.submission3.ui.setting.SettingViewModel

class ViewModelFactory (private val repository: UserRepository,
                        private val preference: SettingPreference) :
    ViewModelProvider.NewInstanceFactory(){

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when{


            modelClass.isAssignableFrom(FollowersViewModel::class.java) -> {
                FollowersViewModel(repository) as T
            }

            modelClass.isAssignableFrom(FollowingViewModel::class.java) -> {
                FollowingViewModel(repository) as T
            }

            modelClass.isAssignableFrom(HomeViewModel::class.java) -> {
                HomeViewModel(repository, preference) as T
            }

            modelClass.isAssignableFrom(SearchViewModel::class.java) -> {
                SearchViewModel(repository, preference) as T
            }

            modelClass.isAssignableFrom(FavoriteViewModel::class.java) -> {
                FavoriteViewModel(repository) as T
            }

            modelClass.isAssignableFrom(DetailViewModel::class.java) -> {
                DetailViewModel(repository) as T
            }

            modelClass.isAssignableFrom(SettingViewModel::class.java) -> {
                SettingViewModel(preference) as T
            }

            else -> throw Throwable("Unknown ViewModel class: " + modelClass.name)
        }
    }
}