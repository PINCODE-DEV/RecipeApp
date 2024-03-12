package com.softanime.recipeapp.presentation.viewModels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.softanime.recipeapp.data.models.register.BodyRegister
import com.softanime.recipeapp.data.models.register.ResponseRegister
import com.softanime.recipeapp.data.repository.RegisterRepo
import com.softanime.recipeapp.utils.NetworkRequest
import com.softanime.recipeapp.utils.NetworkResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(
    private val repo: RegisterRepo
) : ViewModel() {
    // Data
    val registerData = MutableLiveData<NetworkRequest<ResponseRegister>>()

    fun callRegisterApi(apiKey: String, body: BodyRegister) = viewModelScope.launch {
        registerData.value = NetworkRequest.LOADING()
        val response = repo.postRegister(apiKey, body)
        registerData.value = NetworkResponse(response).generalNetworkResponse()
    }
}