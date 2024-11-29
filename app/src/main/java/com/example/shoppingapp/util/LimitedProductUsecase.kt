package com.example.shoppingapp.util

import com.example.shoppingapp.usecase.GetLimitedProducts
import com.example.shoppingapp.usecase.GetSingleLimitedProduct

data class LimitedProductUsecase(var getLimitedProducts: GetLimitedProducts,
var getSingleProduct : GetSingleLimitedProduct)