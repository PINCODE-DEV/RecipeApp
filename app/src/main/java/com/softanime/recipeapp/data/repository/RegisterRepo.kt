package com.softanime.recipeapp.data.repository

import com.softanime.recipeapp.data.models.register.BodyRegister
import com.softanime.recipeapp.data.sources.RemoteDataSource
import dagger.hilt.android.scopes.ActivityRetainedScoped
import javax.inject.Inject

@ActivityRetainedScoped
class RegisterRepo @Inject constructor(private val remote: RemoteDataSource) {
    suspend fun postRegister(apiKey: String, body: BodyRegister) = remote.postRegister(apiKey, body)
}