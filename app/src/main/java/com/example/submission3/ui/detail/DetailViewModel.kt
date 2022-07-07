package com.example.submission3.ui.detail

import androidx.lifecycle.ViewModel
import com.example.submission3.data.UserRepository

class DetailViewModel(private val userRepository: UserRepository): ViewModel() {

    suspend fun getUserByUsername(username: String) = userRepository.getUserByUsername(username)

    suspend fun deleteFavorite(username: String) = userRepository.deleteFavorite(username)

    suspend fun addToFavorite(username: String,
                              name: String,
                              repository: Int,
                              followers: Int,
                              following: Int,
                              company: String,
                              location: String,
                              image: String) = userRepository.addToFavorite(username, name, repository, followers, following, company, location, image)
}