package com.softanime.recipeapp.data.sources

import com.softanime.recipeapp.data.models.register.BodyRegister
import com.softanime.recipeapp.data.network.ApiServices
import javax.inject.Inject

class RemoteDataSource @Inject constructor(private val api: ApiServices) {
    suspend fun postRegister(apiKey: String, body: BodyRegister) = api.postRegister(apiKey, body)
}