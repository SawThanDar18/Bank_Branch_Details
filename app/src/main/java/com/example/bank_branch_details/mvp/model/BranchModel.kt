package com.example.bank_branch_details.mvp.model

import android.provider.ContactsContract
import com.example.bank_branch_details.network.DataImpl

class BranchModel {

    companion object{
        private var INSTANCE : BranchModel? = null
        fun getInstance() : BranchModel {
            if(INSTANCE == null){
                INSTANCE = BranchModel()
            }
            val i = INSTANCE
            return i!!
        }
    }

    fun getRequestAuth(){
        DataImpl.getInstance().getRequestAuth()
    }

    fun getBranchDetail(){
        DataImpl.getInstance().getBranchDetail()
    }

    fun getBankLocation(){
        DataImpl.getInstance().getBankLocation()
    }

}