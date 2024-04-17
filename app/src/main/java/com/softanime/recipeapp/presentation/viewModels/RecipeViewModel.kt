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
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class RecipeViewModel @Inject constructor(private val repo: RecipeRepo) : ViewModel() {

    // Live Data
    val popularData = MutableLiveData<NetworkRequest<ResponseRecipe>>()
    val recipeData = MutableLiveData<NetworkRequest<ResponseRecipe>>()

    // ---Popular---
    // Queries
    fun popularQueries(): HashMap<String, String> {
        val queries = HashMap<String, String>()
        queries[Constants.API_KEY] = Constants.MY_API_KEY
        queries[Constants.SORT] = Constants.POPULARITY
        queries[Constants.ADD_RECIPE_INFORMATION] = Constants.TRUE
        return queries
    }

    // Api
    fun callPopularApi(queries: Map<String, String>) = viewModelScope.launch {
        popularData.value = NetworkRequest.LOADING()
        val response = repo.remote.getRecipes(queries)
        popularData.value = NetworkResponse(response).generalNetworkResponse()
    }

    // ---Recipe---
    // Queries
    fun recipeQueries(): HashMap<String, String> {
        val queries = HashMap<String, String>()
        queries[Constants.API_KEY] = Constants.MY_API_KEY
        queries[Constants.TYPE] = Constants.MAIN_COURSE
        queries[Constants.DIET] = Constants.GLUTEN_FREE
        queries[Constants.NUMBER] = Constants.LIMITED_COUNT.toString()
        queries[Constants.ADD_RECIPE_INFORMATION] = Constants.TRUE
        return queries
    }

    // Api
    fun callRecipeApi(queries: Map<String, String>) = viewModelScope.launch {
        recipeData.value = NetworkRequest.LOADING()
        val response = repo.remote.getRecipes(queries)
        recipeData.value = recipeNetworkRequest(response)
    }
    private fun recipeNetworkRequest(response : Response<ResponseRecipe>) : NetworkRequest<ResponseRecipe>{
        return when{
            response.message().contains("timeout") -> NetworkRequest.ERROR("Timeout")
            response.code() == 401 -> NetworkRequest.ERROR("You are not authorized!")
            response.code() == 402 -> NetworkRequest.ERROR("Your free plan finished!")
            response.code() == 422 -> NetworkRequest.ERROR("ApiKey not found!")
            response.code() == 500 -> NetworkRequest.ERROR("Please try again!")
            response.body()!!.results.isNullOrEmpty() -> NetworkRequest.ERROR("Not found any recipe!")
            response.isSuccessful -> NetworkRequest.SUCCESS(response.body()!!)
            else -> NetworkRequest.ERROR(response.message())
        }
    }
}