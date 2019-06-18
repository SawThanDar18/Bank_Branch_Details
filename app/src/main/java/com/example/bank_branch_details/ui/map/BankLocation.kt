package com.example.bank_branch_details.ui.map

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.Toast
import com.example.bank_branch_details.R
import com.example.bank_branch_details.mvp.presenter.BankLocationPresenter
import com.example.bank_branch_details.mvp.view.BankLocationView
import com.example.bank_branch_details.network.response.BranchCodeResponse
import com.google.android.gms.location.LocationResult
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions

class BankLocation : AppCompatActivity(), BankLocationView, OnMapReadyCallback {

    private var googleMap : GoogleMap? = null
    lateinit var latLng : LatLng
    var location : Location? = null
    private lateinit var presenter : BankLocationPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.map)

        presenter = BankLocationPresenter(this)
        presenter.startLoadingBankLocation()

        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map_fragment) as SupportMapFragment
        mapFragment.getMapAsync(this)

       /*location= Location(this, object :locationListener{
            override fun locationResponse(locationResult: LocationResult) {
                googleMap?.clear()
                val current = LatLng(locationResult.lastLocation.latitude, locationResult.lastLocation.longitude)
                googleMap?.addMarker(MarkerOptions().position(current).title("Position").snippet("current"))
                googleMap?.moveCamera(CameraUpdateFactory.newLatLngZoom(current, 14f))
        }
       })*/
    }

    override fun onMapReady(map: GoogleMap?) {

        googleMap = map
        presenter.startLoadingBankLocation()
    }

    override fun showPrompt(message: String) {
        Toast.makeText(applicationContext, message, Toast.LENGTH_LONG).show()
    }

    override fun showBankLocation(branchCodeResponse: BranchCodeResponse) {

        latLng = LatLng(branchCodeResponse.access_BranchInfo!!.latitude!!, branchCodeResponse.access_BranchInfo!!.longitude!!)
        val markerOptions = MarkerOptions()
        markerOptions.position(latLng)
        markerOptions.title(branchCodeResponse.access_BranchInfo.branch_name)
        markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ROSE))
        googleMap!!.addMarker(markerOptions)

        googleMap!!.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 12f))
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