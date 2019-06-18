package com.example.bank_branch_details.network.api

import com.example.bank_branch_details.network.model.Access_BranchCode
import com.example.bank_branch_details.network.model.Access_BranchInfo
import com.example.bank_branch_details.network.response.BranchCodeResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST

internal interface RequestBranchDetailApi {

    @POST("api/branchDetail")
    fun getBranchDetail(@Header("Authorization") authHeader : String, @Body access_BranchCode: Access_BranchCode) : Call<BranchCodeResponse>

    @POST("api/branchDetail")
    fun getCurrentPosition(@Header("Authorization") authHeader : String, @Body access_BranchCode: Access_BranchCode) : Call<BranchCodeResponse>
}