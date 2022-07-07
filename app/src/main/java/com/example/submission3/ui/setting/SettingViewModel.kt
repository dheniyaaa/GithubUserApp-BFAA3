package com.example.submission3.ui.setting

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.submission3.utils.SettingPreference
import kotlinx.coroutines.launch

class SettingViewModel(private val settingPreference: SettingPreference) : ViewModel() {

    fun getThemeSettings(): LiveData<Boolean> = settingPreference.getThemeSetting().asLiveData()

    fun saveThemeSetting(isDarkModeActive: Boolean){
        viewModelScope.launch {
            settingPreference.saveThemeSetting(isDarkModeActive)
        }
    }
}