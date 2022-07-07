package com.example.submission3.data


import androidx.lifecycle.LiveData
import com.example.submission3.data.source.local.entity.UserEntity
import com.example.submission3.data.source.local.entity.UserModel
import com.example.submission3.vstate.Resource

interface UserDataSource {
    suspend fun getUserByUsername(username: String): UserEntity?
    suspend fun deleteFavorite(username: String)
    suspend fun addToFavorite(username: String,
                              name:String,
                              repository:Int,
                              followers:Int,
                              following:Int,
                              company:String,
                              location:String,
                              image:String)

    fun getAllFavoriteUser(): LiveData<List<UserEntity>>?

    fun getFollowers(username: String): LiveData<Resource<out ArrayList<UserModel>>>

    fun getUser(username: String): LiveData<Resource<out ArrayList<UserModel>>>

    fun getFollowing(username: String): LiveData<Resource<out ArrayList<UserModel>>>
}