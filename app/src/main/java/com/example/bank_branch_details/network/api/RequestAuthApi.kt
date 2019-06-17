package com.example.bank_branch_details.network.api

import com.example.bank_branch_details.network.model.Access_Token
import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

internal interface RequestAuthApi {

    @FormUrlEncoded
    @POST("api/oauth/requesttoken")
    fun getRequestAuth(@Field("username") username : String,
                        @Field("password") password : String,
                        @Field("grant_type") grant_type : String) : Call<Access_Token>
}