package com.example.shoppingapp.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.shoppingapp.dataclass.LoginResponse
import com.example.shoppingapp.network.NetworkResult
import com.example.shoppingapp.repository.ShoppingRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class LoginViewModel
    @Inject
    constructor(var shoppingRepository: ShoppingRepository): ViewModel() {

     var username : MutableLiveData<String> = MutableLiveData()
     var password : MutableLiveData<String> = MutableLiveData()

    private var _loginresponse = MutableLiveData<NetworkResult<LoginResponse>>()
    var loginresponse : LiveData<NetworkResult<LoginResponse>> = _loginresponse

    fun doLogin(){
        Log.d("ShopKart","Username is $username")
        viewModelScope.launch {
                shoppingRepository.doLogin(username.value ?: "", password.value ?: "")
                    .collect {
                        try {
                            _loginresponse.postValue(it!!)
                        } catch (e: Exception) {
                            e.printStackTrace()
                            Log.d("ShopKart", "The issue is ${e.message}")
                        }
               }
        }
    }

}