package com.example.shoppingapp.ui

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import com.example.shoppingapp.R
import com.example.shoppingapp.databinding.ActivityLimitedProductViewBinding
import com.example.shoppingapp.network.NetworkResult
import com.example.shoppingapp.viewmodel.SingleLimitedProductViewmodel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LimitedProductViewActivity : AppCompatActivity() {

    private var TAG : String = "ShopKart"
    var id : Int? = null
    private val singleLimitedProductViewmodel : SingleLimitedProductViewmodel by viewModels()
    lateinit var binding: ActivityLimitedProductViewBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
     //   setContentView(R.layout.activity_limited_product_view)
        binding = DataBindingUtil.setContentView(this,R.layout.activity_limited_product_view)
        binding.singleproductviewmodel = singleLimitedProductViewmodel
        binding.lifecycleOwner = this

        binding.imgBack.setOnClickListener {
            finish()
        }

        var data = intent.extras
        id = data!!.getInt("ID")
        Toast.makeText(this,"The id is $id",Toast.LENGTH_SHORT).show()

        try {
            singleLimitedProductViewmodel.getSingleLimitedProduct(id.toString())
        }catch (e : Exception){
            e.printStackTrace()
            Log.d(TAG,"The error is ${e.message}")
        }

        singleLimitedProductViewmodel.loading.observe(this, Observer {
            if (it){
                binding.scrollview.visibility = View.GONE
                binding.progressbar.visibility = View.VISIBLE
            }else{
                binding.progressbar.visibility = View.GONE
                binding.scrollview.visibility = View.VISIBLE
            }
        })

        singleLimitedProductViewmodel.errormessage.observe(this, Observer {
            Toast.makeText(this,it.toString(),Toast.LENGTH_SHORT).show()
        })
    }
}