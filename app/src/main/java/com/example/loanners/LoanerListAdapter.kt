package com.example.loanners

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class LoanerListAdapter internal constructor(context: Context) : RecyclerView.Adapter<LoanerListAdapter.LoanerViewHolder>() {

    private var inflater: LayoutInflater = LayoutInflater.from(context)
    private var loaners = emptyList<Loaner>() // Cached copy of user

    constructor(context: Context, loaners: ArrayList<Loaner>) : this(context) {
        this.inflater = LayoutInflater.from(context)
        this.loaners = loaners
    }

    inner class LoanerViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val textViewName: TextView = itemView.findViewById(R.id.textViewLoanName)
        val textViewDate: TextView = itemView.findViewById(R.id.textViewAnnounceDate)
        val textViewDetails: TextView = itemView.findViewById(R.id.textViewCompanyDetails)
        val imageView:ImageView = itemView.findViewById(R.id.imageView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LoanerViewHolder {
        val itemView = inflater.inflate(R.layout.loaner_item, parent, false)
        return LoanerViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: LoanerViewHolder, position: Int) {
        val current = loaners[position]
        holder.imageView.setImageResource(current.image)
        holder.textViewName.text = current.loanName
        holder.textViewDate.text = current.announceDate
        holder.textViewDetails.text = current.loanDetails
    }

    override fun getItemCount() = loaners.size
}