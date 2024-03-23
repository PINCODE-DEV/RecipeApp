package com.softanime.recipeapp.presentation.viewModels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.softanime.recipeapp.data.models.recipe.ResponseRecipe
import com.softanime.recipeapp.data.repository.RecipeRepo
import com.softanime.recipeapp.utils.Constants
import com.softanime.recipeapp.utils.NetworkRequest
import com.softanime.recipeapp.utils.NetworkResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RecipeViewModel @Inject constructor(private val repo: RecipeRepo) : ViewModel() {

    val popularData = MutableLiveData<NetworkRequest<ResponseRecipe>>()

    // Popular
    // Queries
    fun popularQueries() : HashMap<String,String> {
        val queries = HashMap<String,String>()
        queries[Constants.API_KEY] = Constants.MY_API_KEY
        queries[Constants.SORT] = Constants.POPULARITY
        queries[Constants.ADD_RECIPE_INFORMATION] = Constants.TRUE
        return queries
    }
    // Api
    fun callPopularApi(queries: Map<String,String>) = viewModelScope.launch {
        popularData.value = NetworkRequest.LOADING()
        val response = repo.remote.getRecipes(queries)
        popularData.value = NetworkResponse(response).generalNetworkResponse()
    }
}