package com.example.submission3.ui.search

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.example.submission3.data.UserRepository
import com.example.submission3.utils.SettingPreference

class SearchViewModel(private val userRepository: UserRepository, private val settingPreference: SettingPreference) : ViewModel() {

    fun getUser(username: String) = userRepository.getUser(username)
    fun getThemeSetting(): LiveData<Boolean> = settingPreference.getThemeSetting().asLiveData()

}