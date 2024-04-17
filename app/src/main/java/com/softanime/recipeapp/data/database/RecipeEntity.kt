package com.softanime.recipeapp.data.database

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.softanime.recipeapp.data.models.recipe.ResponseRecipe
import com.softanime.recipeapp.utils.Constants

@Entity(tableName = Constants.RECIPE_TABLE)
data class RecipeEntity(
    @PrimaryKey(autoGenerate = false)
    var id: Int = 0,
    var response: ResponseRecipe
)