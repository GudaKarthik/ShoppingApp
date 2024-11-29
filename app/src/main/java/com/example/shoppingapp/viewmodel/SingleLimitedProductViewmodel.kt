package com.example.shoppingapp.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.shoppingapp.dataclass.LimitedProducts
import com.example.shoppingapp.network.NetworkResult
import com.example.shoppingapp.util.LimitedProductUsecase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class SingleLimitedProductViewmodel
    @Inject
    constructor(var limitedProductUsecase: LimitedProductUsecase): ViewModel() {


    private var TAG : String = "ShopKart"
    private var _singleLimitedProduct : MutableLiveData<NetworkResult<LimitedProducts>> = MutableLiveData()
    var singleLimitedProduct : LiveData<NetworkResult<LimitedProducts>> = _singleLimitedProduct

    var category : MutableLiveData<String> = MutableLiveData()
    var title : MutableLiveData<String> = MutableLiveData()
    var image : MutableLiveData<String> = MutableLiveData()
    var price : MutableLiveData<String> = MutableLiveData()
    var description : MutableLiveData<String> = MutableLiveData()
    var rating : MutableLiveData<String> = MutableLiveData()
    var count : MutableLiveData<String> = MutableLiveData()

    private var _loading : MutableLiveData<Boolean> = MutableLiveData()
    var loading : LiveData<Boolean> = _loading

    private var _errormessage : MutableLiveData<String> = MutableLiveData()
    var errormessage : LiveData<String> = _errormessage

    fun getSingleLimitedProduct(id : String){
        try {
            viewModelScope.launch {
                withContext(Dispatchers.Main) {
                    limitedProductUsecase.getSingleProduct.invoke(id).collect {
                        _singleLimitedProduct.postValue(it)
                        when(it){
                            is NetworkResult.Loading ->{
                                _loading.value = true
                            }

                            is NetworkResult.Success -> {
                                _loading.value = false
                                category.value = it.data.category.toString()
                                title.value = it.data.title.toString()
                                price.value = "₹ " + it.data.price.toString()
                                image.value = it.data.image.toString()
                                description.value = it.data.description.toString()
                                rating.value = it.data.rating!!.rate.toString()
                                count.value = it.data.rating!!.count.toString() + " ratings"
                            }

                            is NetworkResult.Error ->{
                                _loading.value = false
                                _errormessage.value = it.message
                            }

                            is NetworkResult.Failure ->{

                            }
                        }
                        Log.d("ShopKart", "The details are $it")
                    }
                }
            }
        }catch (e : Exception){
            e.printStackTrace()
            Log.d(TAG,"The issue is ${e.message}")
        }
    }
}