package com.example.shoppingapp.ui

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.databinding.DataBindingUtil
import com.example.shoppingapp.R
import com.example.shoppingapp.databinding.ActivityProfileBinding
import com.example.shoppingapp.viewmodel.ProfileViewModel

class ProfileActivity : AppCompatActivity() {

    lateinit var profileBinding: ActivityProfileBinding
    private val profileviewmodel : ProfileViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    //    setContentView(R.layout.activity_profile)

        profileBinding = DataBindingUtil.setContentView(this,R.layout.activity_profile)
        profileBinding.profileviewmodel = profileviewmodel
        profileBinding.lifecycleOwner = this

    }
}