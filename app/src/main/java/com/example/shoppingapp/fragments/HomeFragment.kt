package com.example.shoppingapp.fragments

import android.Manifest
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.content.ContextCompat.getSystemService
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.shoppingapp.R
import com.example.shoppingapp.adapter.DashboardCategoryAdapter
import com.example.shoppingapp.adapter.LimitedProductsAdapter
import com.example.shoppingapp.adapter.onClickDashboard
import com.example.shoppingapp.adapter.onClickLimitedProduct
import com.example.shoppingapp.data.Constants
import com.example.shoppingapp.databinding.FragmentHomeBinding
import com.example.shoppingapp.dataclass.DashboardCategories
import com.example.shoppingapp.dataclass.LimitedProducts
import com.example.shoppingapp.network.NetworkResult
import com.example.shoppingapp.ui.LimitedProductViewActivity
import com.example.shoppingapp.ui.AllProductsActivity
import com.example.shoppingapp.ui.MyLocationActivity
import com.example.shoppingapp.ui.ProfileActivity
import com.example.shoppingapp.ui.SavedProductsActivity
import com.example.shoppingapp.viewmodel.LimitedProductsViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers

@AndroidEntryPoint
class HomeFragment : Fragment(),onClickLimitedProduct, onClickDashboard {

    var dashboardCategories = arrayListOf(DashboardCategories(R.drawable.all,"All"),DashboardCategories(R.drawable.img_6,"Electronics"),DashboardCategories(R.drawable.jewellery,"Jewellery")
    ,DashboardCategories(R.drawable.clothes,"Men"),DashboardCategories(R.drawable.womenclothes,"Women"),
        DashboardCategories(R.drawable.fav_icon,"Saved")
    )
    lateinit var dashboardCategoryAdapter: DashboardCategoryAdapter

    lateinit var layoutManager: GridLayoutManager
    lateinit var homeBinding: FragmentHomeBinding
    private val limitedProductsViewModel : LimitedProductsViewModel by viewModels()
    var category : String = ""

    // Limited Products
    lateinit var limitedProductsAdapter: LimitedProductsAdapter
    lateinit var layoutManager2: LinearLayoutManager

    var scope : CoroutineScope = CoroutineScope(Dispatchers.Main)
    var TAG : String = "ShopKart"
    var CHANNEL_ID : String = "1"

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
      //  var view = inflater.inflate(R.layout.fragment_home, container, false)
        homeBinding = DataBindingUtil.inflate(inflater,R.layout.fragment_home,container,false)
        layoutManager = GridLayoutManager(requireContext(),2)
        homeBinding.recyclerview.layoutManager = layoutManager
        dashboardCategoryAdapter = DashboardCategoryAdapter(dashboardCategories,requireContext(),this)
        homeBinding.recyclerview.adapter = dashboardCategoryAdapter

        limitedProductsViewModel.getLimitedProducts()

        limitedProductsViewModel.limitedProducts.observe(requireActivity(), Observer {

            when(it){

                is NetworkResult.Error ->{
                    Toast.makeText(requireContext(),it.message,Toast.LENGTH_SHORT).show()
                }

                is NetworkResult.Success -> {
                    homeBinding.progressbar.visibility = View.GONE
                    homeBinding.txtExclusiveDeals.visibility = View.VISIBLE
                    limitedProductsAdapter = LimitedProductsAdapter(it.data,requireContext(),this)
                    layoutManager2 = LinearLayoutManager(requireContext())
                    homeBinding.recyclerview2.layoutManager = layoutManager2
                    homeBinding.recyclerview2.adapter = limitedProductsAdapter
                    limitedProductsAdapter.notifyDataSetChanged()

                    Log.d(TAG,it.data.toString())
                }

                is NetworkResult.Failure -> {
                    Toast.makeText(requireContext(),it.error,Toast.LENGTH_SHORT).show()
                }

                is NetworkResult.Loading -> {
                    homeBinding.progressbar.visibility = View.VISIBLE
                }
            }
        })

        homeBinding.imgProfile.setOnClickListener {
            var intent = Intent(requireContext(),ProfileActivity::class.java)
            startActivity(intent)
        }

        homeBinding.myLocationImg.setOnClickListener {
            var intent = Intent(requireContext(),MyLocationActivity::class.java)
            startActivity(intent)
        }

        return homeBinding.root
    }

    private fun notificationChannel(){
        val notificationManager =
            requireContext().getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        // Create Notification Channel for Android O+
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                CHANNEL_ID,
                "Default Channel",
                NotificationManager.IMPORTANCE_HIGH
            )
            notificationManager.createNotificationChannel(channel)
        }
    }

    override fun onClick(data: LimitedProducts) {
        var intent = Intent(requireContext(),LimitedProductViewActivity::class.java)
        intent.putExtra("ID",data.id)
        startActivity(intent)
    }

    override fun onClickDashboard(data: DashboardCategories) {
        if (data.title == "Electronics"){
            category = Constants.ELECTRONICS
        }else if (data.title == "Jewellery"){
            category = Constants.JEWELLERY
        }else if (data.title == "Men"){
            category = Constants.MENCLOTHING
        }else if (data.title == "Women"){
            category = Constants.WOMENCLOTHING
        }else if (data.title == "All"){
            category = "All"
        }

        if (data.title == "Saved"){
            var intent = Intent(requireContext(),SavedProductsActivity::class.java)
            startActivity(intent)
        }else{
            var intent = Intent(requireContext(),AllProductsActivity::class.java)
            intent.putExtra(Constants.CATEGORY,category)
            startActivity(intent)
        }
    }


}