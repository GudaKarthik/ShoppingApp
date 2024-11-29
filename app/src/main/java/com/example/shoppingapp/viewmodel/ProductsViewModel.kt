package com.example.shoppingapp.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.shoppingapp.dataclass.FavProducts
import com.example.shoppingapp.dataclass.LimitedProducts
import com.example.shoppingapp.network.NetworkResult
import com.example.shoppingapp.util.AllProductsUseCase
import com.example.shoppingapp.util.FavProductsUsecase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class ProductsViewModel
@Inject
constructor(var productsUseCase: AllProductsUseCase,var favProductsUsecase: FavProductsUsecase) : ViewModel(){

    private var _limitedProducts : MutableLiveData<ArrayList<LimitedProducts>> = MutableLiveData()
    var limitedProducts : LiveData<ArrayList<LimitedProducts>> = _limitedProducts

    private var _loading : MutableLiveData<Boolean> = MutableLiveData()
    var loading : LiveData<Boolean> = _loading

    private var _errormessage : MutableLiveData<String> = MutableLiveData()
    var errormessage : LiveData<String> = _errormessage

    private var _favProducts : MutableLiveData<List<FavProducts>> = MutableLiveData()
    var favProducts : LiveData<List<FavProducts>> = _favProducts

    /**
     * All Products
     */
    fun getAllProducts(sort : String,category : String){
        viewModelScope.launch {
            productsUseCase.getAllProducts.invoke(sort,category).collect{

                when(it){
                    is NetworkResult.Loading -> {
                        _loading.value = true
                    }

                    is NetworkResult.Success ->{
                        _loading.value = false
                        _limitedProducts.postValue(it.data)
                    }

                    is NetworkResult.Error -> {
                        _errormessage.value = it.message
                    }
                    is NetworkResult.Failure -> {
                        _errormessage.value = it.error.toString()
                    }
                }
            }
        }
    }

    /**
     * Insert New Product to favourites
     */
    fun insertProduct(favProducts: FavProducts){
        viewModelScope.launch {
            withContext(Dispatchers.IO){
                favProductsUsecase.insertProduct(favProducts)
            }
        }

    }

    /**
     * Get Favourite Products
     */
    fun getFavProducts(){
        favProductsUsecase.getFavProducts.invoke().map {
            Log.d("ShopKart", "Favlist is $it")
            _favProducts.postValue(it)
        }.launchIn(viewModelScope)
    }

    /**
     * Delete Product from favourites
     */
    fun deleteProduct(favProducts: FavProducts,id:String){
        viewModelScope.launch {
            withContext(Dispatchers.IO){
                favProductsUsecase.deleteProduct.invoke(favProducts,id)
            }
        }
    }

}