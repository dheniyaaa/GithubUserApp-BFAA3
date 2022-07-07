package com.example.submission3.data.source.remote

import com.example.submission3.data.source.local.entity.UserModel
import com.example.submission3.data.source.remote.network.ApiConfig
import com.example.submission3.data.source.remote.response.ApiResponse
import com.example.submission3.data.source.remote.response.DetailResponse


class RemoteDataSource {


    suspend fun getUser(username: String): ApiResponse<ArrayList<UserModel>> {
        val listUser = ArrayList<UserModel>()

        return try {
            val response = ApiConfig.getApiService().searchUser(username)
            val responItem = response.body()

            if (response.isSuccessful && responItem != null){
                for (item in responItem.items){

                    val users = ApiConfig.getApiService().getDetailUser(item.username)
                        .body() as DetailResponse

                    val userData = UserModel(
                        users.username.toString(),
                        users.name ?:"-",
                        users.repositories ?:0,
                        users.followers ?:0,
                        users.following ?:0,
                        users.company ?:"-",
                        users.location ?:"-",
                        users.image ?:"-",
                        )
                    listUser.add(userData)

                }
                ApiResponse.success(listUser)

            } else{

                ApiResponse.error("Data not found", listUser)
            }
        }
        catch (e: Exception){
            ApiResponse.error("$e", listUser)
        }
    }

    suspend fun getFollowers(username: String): ApiResponse<ArrayList<UserModel>>{
        val listUser = ArrayList<UserModel>()

        return try {
            val respone = ApiConfig.getApiService().getFollowers(username)
            val responitem = respone.body()

            if (respone.isSuccessful && responitem != null){
                for (item in responitem){
                    val users = ApiConfig.getApiService().getDetailUser(item.username)
                        .body() as DetailResponse

                    val userData = UserModel(
                        users.username.toString(),
                        users.name ?:"-",
                        users.repositories ?:0,
                        users.followers ?:0,
                        users.following ?:0,
                        users.company ?:"-",
                        users.location ?:"-",
                        users.image ?:"-",
                    )
                    listUser.add(userData)
                }
                ApiResponse.success(listUser)
            } else {
                ApiResponse.error("Data not Found", listUser)
            }
        } catch (e: Exception){
            ApiResponse.error("$e", listUser)
        }
    }

    suspend fun getFollowing(username: String): ApiResponse<ArrayList<UserModel>> {
        val listUser = ArrayList<UserModel>()

        return try {
            val responseFollow = ApiConfig.getApiService().getFollowing(username)
            val responseitem  = responseFollow.body()

            if (responseFollow.isSuccessful && responseitem != null){
                for (item in responseitem){
                    val users = ApiConfig.getApiService().getDetailUser(item.username)
                        .body() as DetailResponse
                    val userData = UserModel(
                        users.username.toString(),
                        users.name ?:"-",
                        users.repositories ?:0,
                        users.followers ?:0,
                        users.following ?:0,
                        users.company ?:"-",
                        users.location ?:"-",
                        users.image ?:"-",
                    )
                    listUser.add(userData)
                }
                ApiResponse.success(listUser)

            } else {
                ApiResponse.error("Data not found", listUser)
            }
        } catch (e: Exception){
            ApiResponse.error("$e", listUser)
        }
    }

}