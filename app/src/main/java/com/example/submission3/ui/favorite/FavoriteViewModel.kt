package com.example.submission3.ui.favorite

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.submission3.data.UserRepository
import com.example.submission3.data.source.local.entity.UserEntity

class FavoriteViewModel(private val userRepository: UserRepository) : ViewModel() {

    fun getAllFavoriteUser(): LiveData<List<UserEntity>>? = userRepository.getAllFavoriteUser()
}