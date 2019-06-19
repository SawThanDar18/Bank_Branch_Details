package com.example.bank_branch_details.mvp.presenter

import com.example.bank_branch_details.event.RestApiEvents
import com.example.bank_branch_details.mvp.model.BranchModel
import com.example.bank_branch_details.mvp.view.TouchPointListView
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe

class TouchPointListPresenter constructor(val touchPointListView: TouchPointListView) : BasePresenter() {

    override fun onStart(){
        if(!EventBus.getDefault().isRegistered(this)){
            EventBus.getDefault().register(this)
        }
    }

    fun startLoadingTouchPointList(){
        touchPointListView.showLoading()
        BranchModel.getInstance().getTouchPointList()
    }

    override fun onStop(){
        if(EventBus.getDefault().isRegistered(this)){
            EventBus.getDefault().unregister(this)
        }
    }

    @Subscribe
    fun onSuccess(event : RestApiEvents.ShowTouchPointList){
        touchPointListView.dismissLoading()
        touchPointListView.showTouchPointList(event.touchPointListResponse)
    }

    @Subscribe
    fun onError(event: RestApiEvents.ErrorInvokingAPIEvent){
        touchPointListView.dismissLoading()
        touchPointListView.showPrompt(event.message)
    }
}