package com.example.submission3.utils

import com.example.submission3.data.source.local.entity.UserEntity
import com.example.submission3.data.source.local.entity.UserModel

class Convert() {
    fun convertListToArrayList(list: List<UserEntity>): ArrayList<UserModel> {
        val listUser = ArrayList<UserModel>()

        list.forEach {
            val userData = UserModel(
                it.username,
                it.name,
                it.repository,
                it.followers,
                it.following,
                it.company,
                it.location,
                it.image
            )
            listUser.add(userData)
        }
        return listUser
    }
}