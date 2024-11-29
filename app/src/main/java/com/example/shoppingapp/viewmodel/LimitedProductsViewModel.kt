package com.example.shoppingapp.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.shoppingapp.dataclass.LimitedProducts
import com.example.shoppingapp.network.NetworkResult
import com.example.shoppingapp.retrofit.ApiService
import com.example.shoppingapp.util.LimitedProductUsecase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LimitedProductsViewModel
@Inject
constructor(var limitedProductUsecase: LimitedProductUsecase) : ViewModel(){

    private var _limitedProducts : MutableLiveData<NetworkResult<ArrayList<LimitedProducts>>> = MutableLiveData()
    var limitedProducts : LiveData<NetworkResult<ArrayList<LimitedProducts>>> = _limitedProducts
    private var scope = CoroutineScope(Dispatchers.Main)

    fun getLimitedProducts(){
        viewModelScope.launch {
            limitedProductUsecase.getLimitedProducts.invoke("5").collect{
                _limitedProducts.postValue(it)
                Log.d("ShopKart","The viewmodel list is $it")
            }
        }

    }

}