package com.example.submission3.ui.detail.following

import androidx.lifecycle.ViewModel
import com.example.submission3.data.UserRepository

class FollowingViewModel(private val userRepository: UserRepository) : ViewModel() {
    fun getFollowingUser(username: String) = userRepository.getFollowing(username)
}