package com.example.loanners

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class ApprovalLoanAdapter internal constructor(context: Context) : RecyclerView.Adapter<ApprovalLoanAdapter.ApprovalViewHolder>() {

    private var inflater: LayoutInflater = LayoutInflater.from(context)
    private var approvalloan = emptyList<Approval_Loan>() // Cached copy of user

    constructor(context: Context, approval: ArrayList<Approval_Loan>) : this(context) {
        this.inflater = LayoutInflater.from(context)
        this.approvalloan = approval
    }

    inner class ApprovalViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val textViewSem: TextView = itemView.findViewById(R.id.textViewSemDisplay)
        val textViewDate: TextView = itemView.findViewById(R.id.textViewAnnounceDate)
        val textViewDetails: TextView = itemView.findViewById(R.id.textViewCompanyDetails)
        val imageView:ImageView = itemView.findViewById(R.id.imageView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ApprovalViewHolder {
        val itemView = inflater.inflate(R.layout.semester_detail, parent, false)
        return ApprovalViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ApprovalViewHolder, position: Int) {
        val current = approvalloan[position]
       // holder.textViewSem.text = current.loanName
        //holder.textViewDate.text = current.announceDate
        //holder.textViewDetails.text = current.loanDetails
    }

    override fun getItemCount() = approvalloan.size
}