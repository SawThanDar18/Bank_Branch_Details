package com.example.bank_branch_details.mvp.view

import com.example.bank_branch_details.network.response.BranchCodeResponse


interface BranchView {

    fun showPrompt(message : String)
    fun showBranchDetails(branchCodeResponse: BranchCodeResponse)
}