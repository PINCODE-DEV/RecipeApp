package com.softanime.recipeapp.data.network

import com.softanime.recipeapp.data.models.register.BodyRegister
import com.softanime.recipeapp.data.models.register.ResponseRegister
import com.softanime.recipeapp.utils.Constants.API_KEY
import retrofit2.Response
import retrofit2.http.POST
import retrofit2.http.Query

interface ApiServices {
    @POST("users/connect")
    suspend fun postRegister(@Query(API_KEY) apiKey: String, body: BodyRegister): Response<ResponseRegister>
}