package com.example.shoppingapp.ui

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.distinctUntilChanged
import androidx.recyclerview.widget.GridLayoutManager
import com.example.shoppingapp.R
import com.example.shoppingapp.adapter.AllProductsAdapter
import com.example.shoppingapp.adapter.CategoryAdapter
import com.example.shoppingapp.adapter.onClickFilter
import com.example.shoppingapp.adapter.onClickProduct
import com.example.shoppingapp.data.Constants
import com.example.shoppingapp.databinding.ActivityAllproductsBinding
import com.example.shoppingapp.dataclass.Categories
import com.example.shoppingapp.dataclass.FavProducts
import com.example.shoppingapp.dataclass.LimitedProducts
import com.example.shoppingapp.dataclass.Sorts
import com.example.shoppingapp.viewmodel.ProductsViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AllProductsActivity : AppCompatActivity(),onClickFilter,onClickProduct {

    private var TAG : String = "ShopKart"
    var categories : ArrayList<Categories> = arrayListOf(
        Categories("1","All"),Categories("2","electronics"),Categories("3","jewelery")
                                                                   ,Categories("4","men's clothing"),Categories("5","women's clothing"))
    var category : String = ""

    var sorting : ArrayList<Sorts> = arrayListOf(Sorts("1","Latest"),Sorts("2","Older"))
    private val productsViewModel : ProductsViewModel by viewModels()
    lateinit var binding : ActivityAllproductsBinding
    lateinit var allProductsAdapter: AllProductsAdapter
    lateinit var layoutManager: GridLayoutManager

    lateinit var categoryAdapter: CategoryAdapter
    lateinit var layoutManager2: GridLayoutManager
    var favProductlist : List<FavProducts>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setContentView(R.layout.activity_products)
        window.statusBarColor = ContextCompat.getColor(this,R.color.productbg)
        binding = DataBindingUtil.setContentView(this,R.layout.activity_allproducts)
        binding.productsviewmodel = productsViewModel
        binding.lifecycleOwner = this

        category = intent.getStringExtra(Constants.CATEGORY).toString()
        if (category != "All"){
            binding.categoryRecycleView.visibility = View.GONE
        }
        binding.txtCategory.text = category

        categoryAdapter = CategoryAdapter(sorting,this,this)
        binding.categoryRecycleView.layoutManager = GridLayoutManager(this,3)
        binding.categoryRecycleView.adapter = categoryAdapter
        categoryAdapter.notifyDataSetChanged()

        productsViewModel.getAllProducts("",category)

        productsViewModel.getFavProducts()


        productsViewModel.favProducts.observe(this, Observer {
            favProductlist = it
        })

        productsViewModel.limitedProducts.observe(this, Observer {
           allProductsAdapter = AllProductsAdapter(it,this,this,favProductlist!!)
           layoutManager = GridLayoutManager(this,2)
           binding.recyclerview.layoutManager = layoutManager
           binding.recyclerview.adapter = allProductsAdapter
            allProductsAdapter.addItem(it)
        //   allProductsAdapter.notifyDataSetChanged()
        })

        productsViewModel.loading.observe(this, Observer {
            if (it){
                binding.recyclerview.visibility = View.GONE
                binding.progressbar.visibility = View.VISIBLE
            }else{
                binding.progressbar.visibility = View.GONE
                binding.recyclerview.visibility = View.VISIBLE
            }
        })

        productsViewModel.errormessage.observe(this, Observer {
            Toast.makeText(this,it,Toast.LENGTH_SHORT).show()
        })

        binding.imgBack.setOnClickListener {
            finish()
        }
    }

    override fun clickFilter(data: Sorts) {
        if(data.id == "1"){
            productsViewModel.getAllProducts(Constants.ASC,"All")
        }else if (data.id == "2"){
            productsViewModel.getAllProducts(Constants.DESC,"All")
        }
    }

    override fun onClickFav(data: LimitedProducts) {

        var favProduct = FavProducts(data.id!!.toString(),data.title.toString(),data.image.toString())
        productsViewModel.insertProduct(favProduct)
        /*productsViewModel.favProducts.distinctUntilChanged().observe(this, Observer { favlist ->

            var filter = favlist.filter {
                it.title.contains(favProduct.title)
                it.productid.contains(favProduct.productid)
                it.image.contains(favProduct.image)
            }.map {
                savedpro = FavProducts(it.productid,it.title,it.image)
                pid = it.productid
                title = it.title
                image = it.image
            }

            if (filter.isEmpty()){
                productsViewModel.insertProduct(favProduct)
           //     Toast.makeText(this,"No Favs",Toast.LENGTH_SHORT).show()
                Log.d(TAG,"No records")
            }else{
                Toast.makeText(this,"Favs",Toast.LENGTH_SHORT).show()
                Log.d(TAG,"Existent")
            }
        })*/

    //    Toast.makeText(this,"Added to favourites",Toast.LENGTH_SHORT).show()
    }

    override fun onRemoveFav(data: LimitedProducts,prodId : String) {
        Toast.makeText(this,"Removed From favourites",Toast.LENGTH_SHORT).show()
        var favProduct = FavProducts(data.id!!.toString(),data.title.toString(),data.image.toString())
        productsViewModel.deleteProduct(favProduct,prodId)
        Log.d(TAG,"The deleted item is $favProduct")
        Log.d(TAG,"After deleting ${favProductlist.toString()}")
    }

    override fun clickProduct(data: LimitedProducts) {
        var intent = Intent(this,LimitedProductViewActivity::class.java)
        intent.putExtra("ID",data.id)
        startActivity(intent)
    }

}