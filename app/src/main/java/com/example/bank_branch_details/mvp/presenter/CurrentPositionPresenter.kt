package com.example.bank_branch_details.mvp.presenter

import com.example.bank_branch_details.event.RestApiEvents
import com.example.bank_branch_details.mvp.model.BranchModel
import com.example.bank_branch_details.mvp.view.CurrentPositionView
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe

class CurrentPositionPresenter constructor(val currentPositionView: CurrentPositionView) : BasePresenter() {

    override fun onStart(){
        if(!EventBus.getDefault().isRegistered(this)){
            EventBus.getDefault().register(this)
        }
    }

    fun startLoadingCurrentPosition(){
        BranchModel.getInstance().getCurrentPosition()
    }

    override fun onStop(){
        if(EventBus.getDefault().isRegistered(this)){
            EventBus.getDefault().unregister(this)
        }
    }

    @Subscribe
    fun onShowCurrentPosition(event : RestApiEvents.ShowCurrentPosition){
        currentPositionView.showCurrentPosition(event.branchCodeResponse)
    }

    @Subscribe
    fun onError(event: RestApiEvents.ErrorInvokingAPIEvent){
        currentPositionView.showPrompt(event.message)
    }
}