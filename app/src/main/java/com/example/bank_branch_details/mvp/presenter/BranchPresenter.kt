package com.example.bank_branch_details.mvp.presenter

import com.example.bank_branch_details.event.RestApiEvents
import com.example.bank_branch_details.mvp.model.BranchModel
import com.example.bank_branch_details.mvp.view.BranchView
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe

class BranchPresenter constructor(val branchView : BranchView) : BasePresenter() {

    override fun onStart(){
        if(!EventBus.getDefault().isRegistered(this)){
            EventBus.getDefault().register(this)
        }
    }

    fun startLoadingBranchDetails(){
        branchView.showLoading()
        BranchModel.getInstance().getRequestAuth()
    }

    override fun onStop(){
        if(EventBus.getDefault().isRegistered(this)){
            EventBus.getDefault().unregister(this)
        }
    }

    @Subscribe
    fun onSuccess(event : RestApiEvents.ShowBranchDetails){
        branchView.dismissLoading()
        branchView.showBranchDetails(event.branchCodeResponse)
        branchView.callBankPhone(event.branchCodeResponse)
        branchView.viewMap(event.branchCodeResponse)
    }

    @Subscribe
    fun onError(event: RestApiEvents.ErrorInvokingAPIEvent){
        branchView.dismissLoading()
        branchView.showPrompt(event.message)
    }
}