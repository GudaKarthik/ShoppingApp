package com.example.shoppingapp.ui

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import com.example.shoppingapp.R
import com.example.shoppingapp.data.Constants
import com.example.shoppingapp.databinding.ActivityMainBinding
import com.example.shoppingapp.network.NetworkResult
import com.example.shoppingapp.viewmodel.LoginViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val loginviewmodel : LoginViewModel by viewModels()
    lateinit var binding : ActivityMainBinding
    private var TAG : String = "ShopKart"
    private lateinit var sharedPreferences: SharedPreferences
    private var token : String = ""
    private var SHARED_PREF : String = "SHARED_PREFERENCES"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
      //  setContentView(R.layout.activity_main)
        binding = DataBindingUtil.setContentView(this,R.layout.activity_main)
        binding.loginviewmodel = loginviewmodel
        binding.lifecycleOwner = this

        sharedPreferences = getSharedPreferences(SHARED_PREF,Context.MODE_PRIVATE)
        token = sharedPreferences.getString(Constants.TOKEN,null) ?: ""

        if (token.isNullOrEmpty()){
            Toast.makeText(this@MainActivity,"Error",Toast.LENGTH_SHORT).show()
        }else{
            var intent = Intent(this@MainActivity,DashboardActivity::class.java)
            startActivity(intent)
        }

        binding.btnlogin.setOnClickListener {
            loginviewmodel.doLogin()
        }

        loginviewmodel.loginresponse.observe(this, Observer {

            when(it){

                is NetworkResult.Error ->{
                    Toast.makeText(this@MainActivity,it.message,Toast.LENGTH_SHORT).show()
                }

                is NetworkResult.Loading ->{
                    Toast.makeText(this,"Loading",Toast.LENGTH_SHORT).show()
                }

                is NetworkResult.Success ->{
                    token = it.data.token

                    var editor = sharedPreferences.edit()
                    editor.putString(Constants.TOKEN,token)
                    editor.apply()
                    Toast.makeText(this@MainActivity,it.data.token,Toast.LENGTH_SHORT).show()
                    var intent = Intent(this@MainActivity,DashboardActivity::class.java)
                    startActivity(intent)
                }

                is NetworkResult.Failure ->{
                    Log.d(TAG,"Failure is ${it.error}")
                }

                else -> Log.d(TAG,"Some issue")
            }
        })

    }

    override fun onStart() {
        super.onStart()

    }
}