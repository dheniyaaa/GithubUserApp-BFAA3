package com.example.submission3.data.source.local.entity

import android.os.Parcelable
import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import java.io.Serializable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize


@Parcelize
@Entity(tableName = "githubUser")
data class UserEntity (

        @ColumnInfo(name = "username")
        @PrimaryKey
        @NonNull
        var username:String = "",

        @ColumnInfo(name = "name")
        var name:String = "",

        @ColumnInfo(name = "repository")
        var repository: Int,

        @ColumnInfo(name = "followers")
        var followers:Int,

        @ColumnInfo(name = "following")
        var following:Int,

        @ColumnInfo(name = "company")
        var company:String = "-",

        @ColumnInfo(name = "location")
        var location:String = "",

        @ColumnInfo(name = "image")
        var image:String = ""

        ) : Serializable, Parcelable