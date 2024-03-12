package com.softanime.recipeapp.data.models.register


import com.google.gson.annotations.SerializedName

data class BodyRegister(
    @SerializedName("email")
    val email: String,
    @SerializedName("firstName")
    val firstName: String,
    @SerializedName("lastName")
    val lastName: String,
    @SerializedName("username")
    val username: String
)