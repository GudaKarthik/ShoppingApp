package com.example.shoppingapp.ui

import android.Manifest
import android.app.Activity
import android.content.pm.PackageManager
import android.location.Location
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.shoppingapp.R
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions

class MyLocationActivity : AppCompatActivity(),OnMapReadyCallback {

    private lateinit var googleMap: GoogleMap

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_my_location)

        var mapFragment = supportFragmentManager
            .findFragmentById(R.id.mapview) as SupportMapFragment
        mapFragment.getMapAsync(this)

        if (ActivityCompat.checkSelfPermission(this,Manifest.permission.ACCESS_FINE_LOCATION) ==
            PackageManager.PERMISSION_GRANTED){
          //  googleMap.isMyLocationEnabled = true
        }else{
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),1)
        }

    }

    override fun onMapReady(googlemap: GoogleMap) {
        googleMap = googlemap
        googleMap.uiSettings.isZoomControlsEnabled = true
       // googleMap.isMyLocationEnabled = true

        var pos : LatLng = LatLng(-37.3159,81.1496)
        googleMap.addMarker(MarkerOptions().position(pos).title("User Location"))
        googleMap.moveCamera(CameraUpdateFactory.newLatLng(pos))
    }
}