package com.softanime.recipeapp.utils

import retrofit2.Response

class NetworkResponse<T> (private val response: Response<T>) {

    fun generalNetworkResponse() : NetworkRequest<T>{
        return when{
            response.message().contains("timeout") -> NetworkRequest.ERROR("Timeout")
            response.code() == 401 -> NetworkRequest.ERROR("You are not authorized!")
            response.code() == 402 -> NetworkRequest.ERROR("Your free plan finished!")
            response.code() == 422 -> NetworkRequest.ERROR("ApiKey not found!")
            response.code() == 500 -> NetworkRequest.ERROR("Please try again!")
            response.isSuccessful -> NetworkRequest.SUCCESS(response.body()!!)
            else -> NetworkRequest.ERROR(response.message())
        }
    }
}