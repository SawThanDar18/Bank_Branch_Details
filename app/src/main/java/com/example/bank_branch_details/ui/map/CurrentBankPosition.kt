package com.example.bank_branch_details.ui.map

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import android.widget.Toast
import com.example.bank_branch_details.R
import com.example.bank_branch_details.mvp.presenter.CurrentPositionPresenter
import com.example.bank_branch_details.mvp.view.CurrentPositionView
import com.example.bank_branch_details.network.response.BranchCodeResponse
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions

class CurrentBankPosition : AppCompatActivity(), CurrentPositionView, OnMapReadyCallback {

    private var googleMap : GoogleMap? = null
    lateinit var latLng : LatLng
    var location : Location? = null
    private lateinit var presenter : CurrentPositionPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.map)

        presenter = CurrentPositionPresenter(this)
        presenter.startLoadingCurrentPosition()

        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map_fragment) as SupportMapFragment
        mapFragment.getMapAsync(this)

       /* location= Location(this, object :locationListener{
            override fun locationResponse(locationResult: LocationResult) {
                googleMap?.clear()
                *//*val current = LatLng(locationResult.lastLocation.latitude, locationResult.lastLocation.longitude)
                googleMap?.addMarker(MarkerOptions().position(current).title("Position").snippet("current"))
                googleMap?.moveCamera(CameraUpdateFactory.newLatLngZoom(current, 14f))*//*
                showCurrentPosition(access_BranchInfo = Access_BranchInfo())
            }
        })*/
    }

    override fun onMapReady(map: GoogleMap?) {

        googleMap = map
        presenter.startLoadingCurrentPosition()
    }

    override fun showPrompt(message: String) {
        Toast.makeText(applicationContext, message, Toast.LENGTH_LONG).show()
    }

    override fun showCurrentPosition(branchCodeResponse: BranchCodeResponse) {
       /* latLng = LatLng(branchCodeResponse.access_BranchInfo!!.latitude!!, branchCodeResponse.access_BranchInfo!!.longitude!!)
        googleMap!!.addMarker(MarkerOptions().position(latLng).title(branchCodeResponse.access_BranchInfo!!.branch_name))
        googleMap!!.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 12.0f))*/

        latLng = LatLng(branchCodeResponse.access_BranchInfo!!.latitude!!, branchCodeResponse.access_BranchInfo!!.longitude!!)
        val markerOptions = MarkerOptions()
        markerOptions.position(latLng)
        markerOptions.title(branchCodeResponse.access_BranchInfo.branch_name)
        markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_VIOLET))
        googleMap!!.addMarker(markerOptions)

        //move map camera
        googleMap!!.moveCamera(CameraUpdateFactory.newLatLng(latLng))
        googleMap!!.animateCamera(CameraUpdateFactory.zoomBy(10f))
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        location?.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }
    override fun onStart() {
        super.onStart()
        presenter.onStart()
        location?.inicializeLocation()
    }
    override fun onPause() {
        super.onPause()
        presenter.onStop()
        location?.stopUpdateLocation()
    }
}