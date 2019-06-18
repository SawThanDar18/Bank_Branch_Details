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
import com.example.bank_branch_details.adapter.RecyclerAdapter
import com.example.bank_branch_details.mvp.presenter.BranchPresenter
import com.example.bank_branch_details.mvp.view.BranchView
import com.example.bank_branch_details.network.response.BranchCodeResponse
import com.example.bank_branch_details.ui.map.CurrentBankPosition
import kotlinx.android.synthetic.main.bank_branch_detail.*

class BankDetailActivity : AppCompatActivity(), BranchView{

    private lateinit var presenter: BranchPresenter

    private lateinit var recyclerview : RecyclerView
    private lateinit var recyclerAdapter : RecyclerAdapter

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.bank_branch_detail)

        presenter = BranchPresenter(this@BankDetailActivity)
        presenter.startLoadingBranchDetails()

        call_image.setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse("tel:" + "09260710508"))
            startActivity(intent)
        }

        map_image.setOnClickListener {
            val intent = Intent(this@BankDetailActivity, CurrentBankPosition::class.java)
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

    override fun onStart(){
        super.onStart()
        presenter.onStart()
    }

    override fun onStop() {
        super.onStop()
        presenter.onStop()
    }
}
