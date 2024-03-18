package com.softanime.recipeapp.di

import com.softanime.recipeapp.data.models.register.BodyRegister
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.FragmentComponent

@Module
@InstallIn(FragmentComponent::class)
object BodyModule {
    @Provides
    fun provideRegisterBody() = BodyRegister()
}