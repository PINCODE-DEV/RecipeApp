package com.softanime.recipeapp.data.models.recipe


import com.google.gson.annotations.SerializedName

data class ResponseRecipe(
    @SerializedName("number")
    val number: Int,
    @SerializedName("offset")
    val offset: Int,
    @SerializedName("results")
    val results: List<Result>,
    @SerializedName("totalResults")
    val totalResults: Int
) {
    data class Result(
        @SerializedName("aggregateLikes")
        val aggregateLikes: Int,
        @SerializedName("cheap")
        val cheap: Boolean,
        @SerializedName("cookingMinutes")
        val cookingMinutes: Int,
        @SerializedName("creditsText")
        val creditsText: String,
        @SerializedName("cuisines")
        val cuisines: List<String>,
        @SerializedName("dairyFree")
        val dairyFree: Boolean,
        @SerializedName("diets")
        val diets: List<String>,
        @SerializedName("dishTypes")
        val dishTypes: List<String>,
        @SerializedName("gaps")
        val gaps: String,
        @SerializedName("glutenFree")
        val glutenFree: Boolean,
        @SerializedName("healthScore")
        val healthScore: Int,
        @SerializedName("id")
        val id: Int,
        @SerializedName("image")
        val image: String,
        @SerializedName("imageType")
        val imageType: String,
        @SerializedName("license")
        val license: String,
        @SerializedName("lowFodmap")
        val lowFodmap: Boolean,
        @SerializedName("occasions")
        val occasions: List<String>,
        @SerializedName("preparationMinutes")
        val preparationMinutes: Int,
        @SerializedName("pricePerServing")
        val pricePerServing: Double,
        @SerializedName("readyInMinutes")
        val readyInMinutes: Int,
        @SerializedName("servings")
        val servings: Int,
        @SerializedName("sourceName")
        val sourceName: String,
        @SerializedName("sourceUrl")
        val sourceUrl: String,
        @SerializedName("spoonacularScore")
        val spoonacularScore: Double,
        @SerializedName("spoonacularSourceUrl")
        val spoonacularSourceUrl: String,
        @SerializedName("summary")
        val summary: String,
        @SerializedName("sustainable")
        val sustainable: Boolean,
        @SerializedName("title")
        val title: String,
        @SerializedName("vegan")
        val vegan: Boolean,
        @SerializedName("vegetarian")
        val vegetarian: Boolean,
        @SerializedName("veryHealthy")
        val veryHealthy: Boolean,
        @SerializedName("veryPopular")
        val veryPopular: Boolean,
        @SerializedName("weightWatcherSmartPoints")
        val weightWatcherSmartPoints: Int
    )
}