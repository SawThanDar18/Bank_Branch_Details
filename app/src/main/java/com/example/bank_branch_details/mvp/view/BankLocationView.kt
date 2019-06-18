package com.example.bank_branch_details.mvp.view

import com.example.bank_branch_details.network.response.BranchCodeResponse

interface BankLocationView {

    fun showPrompt(message : String)
    fun showBankLocation(branchCodeResponse: BranchCodeResponse)
}