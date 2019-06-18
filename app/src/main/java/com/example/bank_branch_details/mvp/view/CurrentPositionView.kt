package com.example.bank_branch_details.mvp.view

import com.example.bank_branch_details.network.model.Access_BranchInfo
import com.example.bank_branch_details.network.response.BranchCodeResponse

interface CurrentPositionView {

    fun showPrompt(message : String)
    fun showCurrentPosition(branchCodeResponse: BranchCodeResponse)
}