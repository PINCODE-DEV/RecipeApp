package com.softanime.recipeapp.data.repository

import com.softanime.recipeapp.data.sources.RemoteDataSource
import dagger.hilt.android.scopes.ActivityRetainedScoped
import javax.inject.Inject

@ActivityRetainedScoped
class RecipeRepo @Inject constructor(
    private val remoteDataSource: RemoteDataSource
) {
    val remote = remoteDataSource


}