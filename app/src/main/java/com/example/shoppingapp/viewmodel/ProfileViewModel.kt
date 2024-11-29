package com.example.shoppingapp.viewmodel

import androidx.lifecycle.ViewModel
import com.example.shoppingapp.util.UserProfileUseCase
import javax.inject.Inject

class ProfileViewModel
    @Inject
    constructor(var profileUseCase: UserProfileUseCase): ViewModel() {


}