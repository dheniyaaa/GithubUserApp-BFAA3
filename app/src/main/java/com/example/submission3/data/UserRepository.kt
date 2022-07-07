package com.example.submission3.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import com.example.submission3.data.source.local.LocalDataSource
import com.example.submission3.data.source.local.entity.UserEntity
import com.example.submission3.data.source.local.entity.UserModel
import com.example.submission3.data.source.remote.RemoteDataSource
import com.example.submission3.data.source.remote.StatusResponse
import kotlinx.coroutines.Dispatchers
import com.example.submission3.vstate.Resource

class UserRepository (
    private val remoteDataSource: RemoteDataSource,
    private val localDataSource: LocalDataSource, ): UserDataSource{

    override suspend fun getUserByUsername(username: String): UserEntity? =
        localDataSource.getUserByUsername(username)


    override suspend fun deleteFavorite(username: String) {
        localDataSource.deleteFavorite(username)    }

    override suspend fun addToFavorite(
        username: String,
        name: String,
        repository: Int,
        followers: Int,
        following: Int,
        company: String,
        location: String,
        image: String
    ) {
        localDataSource.addToFavorite(username, name, repository, followers, following, company, location, image)
    }

    override fun getAllFavoriteUser(): LiveData<List<UserEntity>>? =
        localDataSource.getAllFavoriteUser()

    override fun getFollowers(username: String) = liveData(Dispatchers.IO) {
        val listUserData = ArrayList<UserModel>()
        val users = remoteDataSource.getFollowers(username)
        emit(Resource.loading(data = null))

        users.let {
            when (it.status){
                StatusResponse.SUCCES -> {
                    emit(Resource.success(it.body))
                }
                StatusResponse.ERROR -> {
                    emit(Resource.error(it.message, listUserData))
                }
                StatusResponse.EMPITY -> {
                    emit(Resource.error(it.message, listUserData))
                }
            }
        }

    }

    override fun getFollowing(username: String) = liveData(Dispatchers.IO){
        val listUserData = ArrayList<UserModel>()
        val users = remoteDataSource.getFollowing(username)
        emit(Resource.loading(data = null))

        users.let {
            when (it.status){
                StatusResponse.SUCCES -> {
                    emit(Resource.success(it.body))
                }
                StatusResponse.ERROR -> {
                    emit(Resource.error(it.message, listUserData))
                }
                StatusResponse.EMPITY -> {
                    emit(Resource.error(it.message, listUserData))
                }
            }
        }
    }

    override fun getUser(username: String) = liveData(Dispatchers.IO) {
        val listUserData = ArrayList<UserModel>()
        val users = remoteDataSource.getUser(username)

        emit(Resource.loading(data = null))

        users.let {
            when (it.status){
                StatusResponse.SUCCES -> {
                    emit(Resource.success(it.body))
                }
                StatusResponse.ERROR -> {
                    emit(Resource.error(it.message, listUserData))
                }
                StatusResponse.EMPITY -> {
                    emit(Resource.error(it.message, listUserData))
                }
            }
        }
    }




}