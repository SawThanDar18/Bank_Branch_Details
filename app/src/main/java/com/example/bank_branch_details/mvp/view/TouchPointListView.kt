package com.example.bank_branch_details.mvp.view

import com.example.bank_branch_details.network.response.TouchPointListResponse

interface TouchPointListView {

    fun showPrompt(message : String)
    fun showLoading()
    fun dismissLoading()
    fun showTouchPointList(touchPointListResponse: TouchPointListResponse)
}