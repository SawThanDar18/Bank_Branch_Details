package com.example.bank_branch_details.ui.map

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.Toast
import com.example.bank_branch_details.R
import com.example.bank_branch_details.mvp.presenter.TouchPointListPresenter
import com.example.bank_branch_details.mvp.view.TouchPointListView
import com.example.bank_branch_details.network.response.TouchPointListResponse
import com.google.android.gms.location.LocationResult
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import kotlinx.android.synthetic.main.bank_branch_detail.*

class MainActivity : AppCompatActivity(), TouchPointListView, OnMapReadyCallback {

    private lateinit var presenter : TouchPointListPresenter
    private lateinit var googleMap: GoogleMap
    private var currentLatLng : LatLng? = null
    private var location : Location? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.map)

        presenter = TouchPointListPresenter(this)
        presenter.startLoadingTouchPointList()

        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map_fragment) as SupportMapFragment
        mapFragment.getMapAsync(this)

        location= Location(
            this,
            object : locationListener {
                override fun locationResponse(locationResult: LocationResult) {
                    googleMap?.clear()
                    googleMap?.addMarker(MarkerOptions().position(currentLatLng!!).title("Position").snippet("current"))
                    googleMap?.moveCamera(CameraUpdateFactory.newLatLngZoom(currentLatLng, 14f))
                }
            })

    }

    override fun showPrompt(message: String) {
        Toast.makeText(applicationContext, message, Toast.LENGTH_LONG).show()
    }

    override fun showLoading() {
        if (!swipeRefresh.isRefreshing) {
            swipeRefresh.isRefreshing = true
        }
    }

    override fun dismissLoading() {
        if (swipeRefresh.isRefreshing) {
            swipeRefresh.isRefreshing = false
        }
    }

    override fun showTouchPointList(touchPointListResponse: TouchPointListResponse) {
        currentLatLng = LatLng(touchPointListResponse.access_TouchPointList!!.currentLat!!, touchPointListResponse.access_TouchPointList!!.currentLong!!)
    }

    override fun onMapReady(map: GoogleMap?) {
        googleMap = map!!
        presenter.startLoadingTouchPointList()
    }

    override fun onStart(){
        super.onStart()
        presenter.onStart()
    }

    override fun onStop() {
        super.onStop()
        presenter.onStop()
    }
}