package com.example.bank_branch_details.network.response

import com.example.bank_branch_details.network.model.Access_BranchCode
import com.example.bank_branch_details.network.model.Access_BranchInfo
import com.example.bank_branch_details.network.model.Access_BranchServices
import com.google.gson.annotations.SerializedName

class BranchCodeResponse {

    @SerializedName("Request")
    val branch : Access_BranchCode? = null

    @SerializedName("BRANCH_INFO")
    val access_BranchInfo : Access_BranchInfo? = null

    @SerializedName("Services")
    val access_BranchServices : ArrayList<Access_BranchServices>? = null
}