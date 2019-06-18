package com.example.bank_branch_details.mvp.presenter

import com.example.bank_branch_details.event.RestApiEvents
import com.example.bank_branch_details.mvp.model.BranchModel
import com.example.bank_branch_details.mvp.view.BankLocationView
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe

class BankLocationPresenter constructor(val bankLocationView: BankLocationView) : BasePresenter() {

    override fun onStart(){
        if(!EventBus.getDefault().isRegistered(this)){
            EventBus.getDefault().register(this)
        }
    }

    fun startLoadingBankLocation(){
        BranchModel.getInstance().getBankLocation()
    }

    override fun onStop(){
        if(EventBus.getDefault().isRegistered(this)){
            EventBus.getDefault().unregister(this)
        }
    }

    @Subscribe
    fun onShowCurrentPosition(event : RestApiEvents.ShowBankLocation){
        bankLocationView.showBankLocation(event.branchCodeResponse)
    }

    @Subscribe
    fun onError(event: RestApiEvents.ErrorInvokingAPIEvent){
        bankLocationView.showPrompt(event.message)
    }
}