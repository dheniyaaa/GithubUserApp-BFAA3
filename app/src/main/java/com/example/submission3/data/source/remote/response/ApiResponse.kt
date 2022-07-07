package com.example.submission3.data.source.remote.response

import com.example.submission3.data.source.remote.StatusResponse

class ApiResponse <T> (val status: StatusResponse, val body: T, val message: String?){

    companion object {
        fun <T> success(body: T): ApiResponse<T> = ApiResponse(StatusResponse.SUCCES, body, null)

        fun <T> error(msg: String, body: T): ApiResponse<T> = ApiResponse(StatusResponse.ERROR, body, msg)
    }
}