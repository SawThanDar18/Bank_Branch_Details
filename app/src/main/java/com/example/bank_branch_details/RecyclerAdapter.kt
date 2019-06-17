package com.example.bank_branch_details

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.example.bank_branch_details.network.model.Access_BranchServices

class RecyclerAdapter(val branch_service : ArrayList<Access_BranchServices>, val mContext : Context) : RecyclerView.Adapter<MyHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyHolder {

        val view = LayoutInflater.from(parent.context).inflate(R.layout.branch_details_recycler_items, parent, false)
        return MyHolder(view, mContext)
    }

    override fun onBindViewHolder(holder: MyHolder, position: Int) {

        holder?.index(branch_service[position])
    }

    override fun getItemCount(): Int {
        return branch_service.size
    }
}