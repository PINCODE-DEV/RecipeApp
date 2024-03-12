package com.softanime.recipeapp.utils

import android.annotation.SuppressLint
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.net.NetworkRequest
import android.os.Build
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject

class NetworkChecker
@Inject constructor(val manager: ConnectivityManager, val request: NetworkRequest) :
    ConnectivityManager.NetworkCallback() {
    private val isNetworkAvailable = MutableStateFlow(false)
    private var capabilities: NetworkCapabilities? = null

    @SuppressLint("ObsoleteSdkInt")
    fun checkNetworkAvailability(): MutableStateFlow<Boolean> {
        // Register
        manager.registerNetworkCallback(request, this)
        // Init Network
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            // Active Network
            val activeNetwork = manager.activeNetwork
            if (activeNetwork == null) {
                isNetworkAvailable.value = false
                return isNetworkAvailable
            }

            // Capability
            capabilities = manager.getNetworkCapabilities(activeNetwork)
            if (capabilities == null) {
                isNetworkAvailable.value = false
                return isNetworkAvailable
            }

            isNetworkAvailable.value = when {
                capabilities!!.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
                capabilities!!.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
                else -> false
            }
        } else {
            manager.run {
                manager.activeNetworkInfo?.run {
                    isNetworkAvailable.value = when (type) {
                        ConnectivityManager.TYPE_WIFI -> true
                        ConnectivityManager.TYPE_MOBILE -> true
                        ConnectivityManager.TYPE_ETHERNET -> true
                        else -> false
                    }
                }
            }
        }
        return isNetworkAvailable
    }
}