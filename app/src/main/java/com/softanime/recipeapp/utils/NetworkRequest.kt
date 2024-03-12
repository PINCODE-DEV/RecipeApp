package com.softanime.recipeapp.utils

sealed class NetworkRequest<T>(val data: T? = null, val message: String? = null) {

    class LOADING<T> : NetworkRequest<T>()
    class SUCCESS<T>(data: T) : NetworkRequest<T>(data)
    class ERROR<T>(message: String?, data: T? = null) : NetworkRequest<T>(data, message)
}