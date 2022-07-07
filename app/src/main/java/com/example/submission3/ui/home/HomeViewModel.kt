package com.example.submission3.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.example.submission3.data.UserRepository
import com.example.submission3.utils.SettingPreference

class HomeViewModel(private val userRepository: UserRepository, private val settingPreference: SettingPreference) : ViewModel() {

    fun getThemeSetting(): LiveData<Boolean> = settingPreference.getThemeSetting().asLiveData()



}