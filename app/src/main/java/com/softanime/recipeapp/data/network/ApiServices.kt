package com.softanime.recipeapp.data.network

import com.softanime.recipeapp.data.models.recipe.ResponseRecipe
import com.softanime.recipeapp.data.models.register.BodyRegister
import com.softanime.recipeapp.data.models.register.ResponseRegister
import com.softanime.recipeapp.utils.Constants.API_KEY
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query
import retrofit2.http.QueryMap

interface ApiServices {
    @POST("users/connect")
    suspend fun postRegister(
        @Query(API_KEY) apiKey: String,
        @Body body: BodyRegister
    ): Response<ResponseRegister>

    @GET("recipes/complexSearch")
    suspend fun getRecipes(@QueryMap queries: Map<String, String>): Response<ResponseRecipe>
}