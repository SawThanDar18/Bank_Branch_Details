package com.example.bank_branch_details.network.model

import com.google.gson.annotations.SerializedName

class Access_BranchInfo {

    @SerializedName("Branch_Name")
    val branch_name : String? = null

    @SerializedName("Address")
    val branch_address : String? = null

    @SerializedName("Phone")
    val branch_phone : String? = null
}