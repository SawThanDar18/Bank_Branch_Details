package com.example.bank_branch_details.ui.map

import com.google.android.gms.location.LocationResult

interface locationListener {
    fun locationResponse(locationResult: LocationResult)
}