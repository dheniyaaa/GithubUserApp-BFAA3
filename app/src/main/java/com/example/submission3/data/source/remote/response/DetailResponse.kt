package com.example.submission3.data.source.remote.response

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class DetailResponse(

	@field:SerializedName("followers")
	val followers: Int?,

	@field:SerializedName("avatar_url")
	val image: String?,

	@field:SerializedName("following")
	val following: Int?,

	@field:SerializedName("name")
	val name: String?,

	@field:SerializedName("company")
	val company: String?,

	@field:SerializedName("location")
	val location: String?,

	@field:SerializedName("public_repos")
	val repositories: Int?,

	@field:SerializedName("login")
	val username: String?
) : Parcelable
