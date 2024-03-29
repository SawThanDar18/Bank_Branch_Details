package com.example.bank_branch_details.ui.detail

import android.content.Intent
import android.net.Uri
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.widget.GridLayout.VERTICAL
import android.widget.TextView
import android.widget.Toast
import com.example.bank_branch_details.R
import com.example.bank_branch_details.ui.detail.adapter.RecyclerAdapter
import com.example.bank_branch_details.mvp.presenter.BranchPresenter
import com.example.bank_branch_details.mvp.view.BranchView
import com.example.bank_branch_details.network.response.BranchCodeResponse
import kotlinx.android.synthetic.main.bank_branch_detail.*

class BankDetailActivity : AppCompatActivity(), BranchView{

    private lateinit var presenter: BranchPresenter

    private lateinit var recyclerview : RecyclerView
    private lateinit var recyclerAdapter : RecyclerAdapter

    private var latitude : Double? = null
    private var longitude : Double? = null
    private var branch_name : String? = null

    private var phone : Array<String>? = null

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.bank_branch_detail)

        presenter = BranchPresenter(this)
        presenter.startLoadingBranchDetails()

        swipeRefresh.setOnRefreshListener {
            presenter.startLoadingBranchDetails()
        }

        refresh_iv.setOnClickListener {
            presenter.startLoadingBranchDetails()
        }

        call_image.setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse("tel:" + phone!![0]))
            startActivity(intent)
        }

        map_image.setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse("geo:<$latitude>,<$longitude>?q=<$latitude>,<$longitude>($branch_name)"))
            startActivity(intent)
        }
    }

    override fun showPrompt(message: String) {
        Toast.makeText(applicationContext, message, Toast.LENGTH_LONG).show()
    }

    override fun showBranchDetails(branchCodeResponse : BranchCodeResponse) {
        val branch_title = findViewById<TextView>(R.id.branch_title)
        val branch_address = findViewById<TextView>(R.id.branch_address)
        val branch_phone = findViewById<TextView>(R.id.branch_phone)

        branch_title.text = branchCodeResponse!!.access_BranchInfo!!.branch_name
        branch_address.text = branchCodeResponse.access_BranchInfo!!.branch_address
        branch_phone.text = branchCodeResponse.access_BranchInfo!!.branch_phone

        recyclerview = findViewById(R.id.recyclerview)
        recyclerAdapter = RecyclerAdapter(branchCodeResponse.access_BranchServices!!, this)
        recyclerview.adapter = recyclerAdapter
        var layoutManager = GridLayoutManager(this, 1, VERTICAL, false)
        recyclerview.setLayoutManager(layoutManager)
    }

    override fun viewMap(branchCodeResponse: BranchCodeResponse) {

        latitude = branchCodeResponse.access_BranchInfo!!.latitude
        longitude = branchCodeResponse.access_BranchInfo!!.longitude
        branch_name = branchCodeResponse.access_BranchInfo!!.branch_name
    }

    override fun callBankPhone(branchCodeResponse: BranchCodeResponse) {
        phone = branchCodeResponse.access_BranchInfo!!.branch_phone!!.split(",").toTypedArray()
    }

    override fun showLoading() {
        if (!swipeRefresh.isRefreshing) {
            swipeRefresh.isRefreshing = true
        }
    }

    override fun dismissLoading() {
        if (swipeRefresh.isRefreshing) {
            swipeRefresh.isRefreshing = false
        }
    }

    override fun onStart(){
        super.onStart()
        presenter.onStart()
    }

    override fun onStop() {
        super.onStop()
        presenter.onStop()
    }
}
