package com.example.bank_branch_details.network.api

import com.example.bank_branch_details.network.model.Access_BranchCode
import com.example.bank_branch_details.network.model.Access_TouchPointList
import com.example.bank_branch_details.network.response.BranchCodeResponse
import com.example.bank_branch_details.network.response.TouchPointListResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST

interface RequestTouchPointListApi {

    @POST("api/getTouchpointListByCategory")
    fun getTouchPointList(@Header("Authorization") authHeader : String, @Body access_TouchPointList: Access_TouchPointList) : Call<TouchPointListResponse>
}