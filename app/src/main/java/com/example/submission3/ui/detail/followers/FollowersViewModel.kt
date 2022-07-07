package com.example.submission3.ui.detail.followers

import androidx.lifecycle.ViewModel
import com.example.submission3.data.UserRepository

class FollowersViewModel(private val userRepository: UserRepository) : ViewModel() {

    fun getFollowers(username: String) = userRepository.getFollowers(username)
}