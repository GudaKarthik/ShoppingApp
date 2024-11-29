package com.example.shoppingapp.network

import com.example.shoppingapp.dataclass.LoginResponse

sealed class NetworkResult<T> {
    data class Error<T>(var message : String) : NetworkResult<T>()

    data class Loading<T>(var isLoading: Boolean) : NetworkResult<T>()
    data class Success<T>(var data : T) : NetworkResult<T>()
    data class Failure<T>(var error : String) : NetworkResult<T>()
}