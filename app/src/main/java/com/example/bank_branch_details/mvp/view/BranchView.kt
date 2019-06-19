package com.example.bank_branch_details.mvp.view

import com.example.bank_branch_details.network.response.BranchCodeResponse


interface BranchView {

    fun showPrompt(message : String)
    fun showLoading()
    fun dismissLoading()
    fun showBranchDetails(branchCodeResponse: BranchCodeResponse)
    fun viewMap(branchCodeResponse: BranchCodeResponse)
    fun callBankPhone(branchCodeResponse: BranchCodeResponse)
}