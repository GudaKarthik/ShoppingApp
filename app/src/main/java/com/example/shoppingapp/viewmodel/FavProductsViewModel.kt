package com.example.shoppingapp.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.room.Insert
import com.example.shoppingapp.dataclass.FavProducts
import com.example.shoppingapp.network.ProductOrder
import com.example.shoppingapp.util.FavProductsUsecase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavProductsViewModel
@Inject
constructor(var favProductsUsecase: FavProductsUsecase) : ViewModel(){

    private var _favProducts : MutableLiveData<List<FavProducts>> = MutableLiveData()
    var favProducts : LiveData<List<FavProducts>> = _favProducts

    fun getFavProducts(){
        favProductsUsecase.getFavProducts.invoke().map {
            Log.d("ShopKart", "Favlist is $it")
            _favProducts.postValue(it)
        }.launchIn(viewModelScope)
    }
}
