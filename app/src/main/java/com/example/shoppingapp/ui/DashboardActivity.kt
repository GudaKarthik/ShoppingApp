package com.example.shoppingapp.ui

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.example.shoppingapp.R
import com.example.shoppingapp.data.Constants
import com.example.shoppingapp.databinding.ActivityDashboardBinding
import com.example.shoppingapp.fragments.AccountFragment
import com.example.shoppingapp.fragments.CategoryFragment
import com.example.shoppingapp.fragments.ExploreFragment
import com.example.shoppingapp.fragments.HomeFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@AndroidEntryPoint
class DashboardActivity : AppCompatActivity() {

    private var SHARED_PREF : String = "SHARED_PREFERENCES"
    private lateinit var sharedPreferences: SharedPreferences
    lateinit var binding: ActivityDashboardBinding
    private var token : String = ""
    var homeFragment = HomeFragment()
    var exploreFragment = ExploreFragment()
    var categoryFragment = CategoryFragment()
    var accountFragment = AccountFragment()
    var backpressed = false
    var scope : CoroutineScope = CoroutineScope(Dispatchers.Main)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    //    setContentView(R.layout.activity_dashboard)
        binding = DataBindingUtil.setContentView(this,R.layout.activity_dashboard)
        sharedPreferences = getSharedPreferences(SHARED_PREF,Context.MODE_PRIVATE)
        token = sharedPreferences.getString(Constants.TOKEN,null)!!

        loadFragment(homeFragment)

        binding.bottomNav.setOnItemSelectedListener {
            when(it.itemId){
                R.id.home -> {
                    loadFragment(homeFragment)
                    true
                }

                R.id.explore -> {
                    loadFragment(exploreFragment)
                    true
                }

                R.id.catgory -> {
                    loadFragment(categoryFragment)
                    true
                }

                R.id.account -> {
                    loadFragment(accountFragment)
                    true
                }

                else -> {
                    true
                }
            }
        }

        binding.btnLogout.setOnClickListener {
            var editor = sharedPreferences.edit()
            editor.clear()
            editor.apply()
            var intent = Intent(this,MainActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

    private fun loadFragment(fragment: Fragment){
        supportFragmentManager.beginTransaction().apply {
            addToBackStack(null)
            replace(R.id.framelayout,fragment)
            commit()
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
            val intent = Intent(Intent.ACTION_MAIN)
            intent.addCategory(Intent.CATEGORY_HOME);
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
            startActivity(intent)
            finish()
            System.exit(0)
    }
}