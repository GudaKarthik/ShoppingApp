package com.example.shoppingapp.usecase

import com.example.shoppingapp.network.NetworkResult
import com.example.shoppingapp.retrofit.ApiService
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetUserProfile
@Inject
constructor(var apiService: ApiService){

    operator fun invoke() = flow {
        emit(NetworkResult.Loading(true))
        var response = apiService.getProductProfile()
        if (response.isSuccessful) {
            emit(NetworkResult.Success(response.body()!!))
        }else{
            emit(NetworkResult.Error("There seems to be a issue"))
        }
    }.catch {
        emit(NetworkResult.Failure(it.message.toString()))
    }
}