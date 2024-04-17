package com.softanime.recipeapp.data.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.softanime.recipeapp.data.models.recipe.ResponseRecipe
import com.softanime.recipeapp.utils.Constants
import kotlinx.coroutines.flow.Flow

@Dao
interface RecipeAppDao {
    // Save Recipe
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveRecipes(response: ResponseRecipe)

    // ReadRecipe
    @Query("SELECT * FROM ${Constants.RECIPE_TABLE} ORDER BY ID ASC")
    fun loadRecipes(): Flow<List<ResponseRecipe>>
}