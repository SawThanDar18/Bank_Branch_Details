package com.example.bank_branch_details.network.model

import com.google.gson.annotations.SerializedName

class Access_Token {

    @SerializedName("access_token")
    val access_token : String? = null

    @SerializedName("token_type")
    val token_type : String? = null

    @SerializedName("expires_in")
    val expires_in : String? = null
}