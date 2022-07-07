package com.example.submission3.data.source.local

import android.app.Application
import androidx.lifecycle.LiveData
import com.example.submission3.data.source.local.entity.UserEntity
import com.example.submission3.data.source.local.room.UserDao
import com.example.submission3.data.source.local.room.UserDatabase

class LocalDataSource(application: Application) {
    private val userDao: UserDao?

    init {
        val userDb = UserDatabase.getInstance(application)
        userDao = userDb.userDao()
    }

    suspend fun getUserByUsername(username: String) = userDao?.getUserByUsername(username)

    suspend fun addToFavorite(
        username: String,
        name:String,
        repository:Int,
        followers:Int,
        following:Int,
        company:String,
        location:String,
        image:String
    ){
        val user = UserEntity(
            username, name, repository, followers, following, company, location, image
        )

        userDao?.insertUser(user)
    }

    suspend fun deleteFavorite(username: String){
        userDao?.deleteUser(username)
    }

    fun getAllFavoriteUser(): LiveData<List<UserEntity>>? = userDao?.getAllUser()
}