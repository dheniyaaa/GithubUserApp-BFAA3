package com.example.submission3.data.source.remote.network

import com.example.submission3.BuildConfig
import com.example.submission3.data.source.remote.response.DetailResponse
import com.example.submission3.data.source.remote.response.FollowResponse
import com.example.submission3.data.source.remote.response.SearchResponse
import retrofit2.Response
import retrofit2.http.*

interface ApiService {

    @Headers("Authorization: token ${BuildConfig.TOKEN}")
    @GET("search/users")
    suspend fun searchUser(
        @Query("q") username: String
    ): Response<SearchResponse>

    @Headers("Authorization: token ${BuildConfig.TOKEN}")
    @GET("users/{username}")
    suspend fun getDetailUser(
        @Path("username") username: String?
    ): Response<DetailResponse>

    @Headers("Authorization: token ${BuildConfig.TOKEN}")
    @GET("users/{username}/followers")
    suspend fun getFollowers(
        @Path("username") username: String
    ): Response<List<FollowResponse>>

    @Headers("Authorization: token ${BuildConfig.TOKEN}")
    @GET("users/{username}/following")
    suspend fun getFollowing(
        @Path("username") username: String
    ): Response<List<FollowResponse>>
}