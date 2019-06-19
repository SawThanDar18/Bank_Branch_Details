package com.example.bank_branch_details.network.model

import com.google.gson.annotations.SerializedName

class Access_TouchPointList (val category : String, val pageNo : String, val range : String, val appVersion : String){

    @SerializedName("currentLat")
    val currentLat : Double? = null

    @SerializedName("currentLong")
    val currentLong : Double? = null
}