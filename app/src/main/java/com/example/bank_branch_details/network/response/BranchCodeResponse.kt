package com.example.bank_branch_details.network.response

import com.example.bank_branch_details.network.model.*
import com.google.gson.annotations.SerializedName

class BranchCodeResponse {

    @SerializedName("Request")
    val access_BranchCode : Access_BranchCode? = null

    @SerializedName("BRANCH_INFO")
    val access_BranchInfo : Access_BranchInfo? = null

    @SerializedName("Services")
    val access_BranchServices : ArrayList<Access_BranchServices>? = null
}