package com.example.bank_branch_details

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.TextView
import com.example.bank_branch_details.network.model.Access_BranchServices

class MyHolder(itemView: View, private val mContext: Context) : RecyclerView.ViewHolder(itemView) {

    private val service_title : TextView

    init {
        service_title = itemView.findViewById<View>(R.id.branch_detail_tv) as TextView
    }

    fun index(branchServices: Access_BranchServices){
        service_title.text = branchServices.service_title
    }
}