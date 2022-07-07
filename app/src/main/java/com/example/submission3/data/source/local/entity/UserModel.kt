package com.example.submission3.data.source.local.entity

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import org.jetbrains.annotations.Nullable

@Parcelize
data class UserModel (
    var username:String,
    var name:String?,
    var repository:Int?,
    var followers:Int?,
    var following:Int?,
    var company:String?,
    var location:String?,
    var image:String?
        ) : Parcelable


