package com.example.bank_branch_details.network.api

import com.example.bank_branch_details.network.response.BranchCodeResponse

interface Data {

    fun getRequestAuth()
    fun getBranchDetail()
}