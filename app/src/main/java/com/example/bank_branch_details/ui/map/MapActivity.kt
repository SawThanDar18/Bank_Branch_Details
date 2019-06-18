package com.example.bank_branch_details.ui.map

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.example.bank_branch_details.R
import com.google.android.gms.location.LocationResult
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions

class MapActivity : AppCompatActivity(), OnMapReadyCallback {

    var location : Location? = null
    private var googleMap : GoogleMap? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.map)

        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map_fragment) as SupportMapFragment
        mapFragment.getMapAsync(this)

        location= Location(this, object :locationListener{
            override fun locationResponse(locationResult: LocationResult) {
                googleMap?.clear()
                val current = LatLng(locationResult.lastLocation.latitude, locationResult.lastLocation.longitude)
                googleMap?.addMarker(MarkerOptions().position(current).title("Position").snippet("current"))
                googleMap?.moveCamera(CameraUpdateFactory.newLatLngZoom(current, 14f))
            }
        })

    }

    override fun onMapReady(map: GoogleMap) {
        googleMap = map
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        location?.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }
    override fun onStart() {
        super.onStart()
        location?.inicializeLocation()
    }
    override fun onPause() {
        super.onPause()
        location?.stopUpdateLocation()
    }
}