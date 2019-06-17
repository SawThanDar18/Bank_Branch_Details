package com.example.bank_branch_details.event

import com.example.bank_branch_details.network.response.BranchCodeResponse

object RestApiEvents {

    class ErrorInvokingAPIEvent(val message : String)
    class ShowBranchDetails(val branchCodeResponse: BranchCodeResponse)
}