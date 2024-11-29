package com.example.shoppingapp.ui

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.shoppingapp.R
import com.example.shoppingapp.adapter.FavProductsAdapter
import com.example.shoppingapp.adapter.onClickFavProduct
import com.example.shoppingapp.databinding.ActivitySavedProductsBinding
import com.example.shoppingapp.dataclass.FavProducts
import com.example.shoppingapp.viewmodel.FavProductsViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SavedProductsActivity : AppCompatActivity(),onClickFavProduct {

    private var TAG : String = "ShopKart"
    private val favProductsViewModel : FavProductsViewModel by viewModels()
    lateinit var favProductsAdapter : FavProductsAdapter
    lateinit var layoutManager: GridLayoutManager
    lateinit var binding: ActivitySavedProductsBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
     //   setContentView(R.layout.activity_saved_products)
        window.statusBarColor = ContextCompat.getColor(this,R.color.savedbg)
        binding = DataBindingUtil.setContentView(this,R.layout.activity_saved_products)
        binding.savedproductviewmodel = favProductsViewModel
        binding.lifecycleOwner = this

        binding.imgBack.setOnClickListener {
            finish()
        }

        favProductsViewModel.getFavProducts()

        favProductsViewModel.favProducts.observe(this, Observer {
            layoutManager = GridLayoutManager(this,2)
            favProductsAdapter = FavProductsAdapter(it,this,this)
            binding.recyclerview.layoutManager = layoutManager
            binding.recyclerview.adapter = favProductsAdapter

            Log.d(TAG,"The fav list is $it")
        })
    }

    override fun onClickFav(favItem: FavProducts) {
        Toast.makeText(this,favItem.productid,Toast.LENGTH_SHORT).show()
        var intent = Intent(this,LimitedProductViewActivity::class.java)
        intent.putExtra("ID",favItem.productid.toInt())
        startActivity(intent)
        Log.d(TAG,"You have clicked on ${favItem.id}")
    }
}