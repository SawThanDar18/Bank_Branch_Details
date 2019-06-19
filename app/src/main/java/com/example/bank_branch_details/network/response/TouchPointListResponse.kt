package com.example.bank_branch_details.network.response

import com.example.bank_branch_details.network.model.Access_ATM
import com.example.bank_branch_details.network.model.Access_Branch
import com.example.bank_branch_details.network.model.Access_TouchPointList
import com.google.gson.annotations.SerializedName

class TouchPointListResponse {

    @SerializedName("Request")
    val access_TouchPointList : Access_TouchPointList? = null

    @SerializedName("ATM")
    val access_ATM : ArrayList<Access_ATM>? = null

    @SerializedName("BRANCH")
    val access_Branch : ArrayList<Access_Branch>? = null
}