package com.example.loanners

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView


class SemesterAdapter internal constructor(context: Context) : RecyclerView.Adapter<SemesterAdapter.SemViewHolder>() {

    private var inflater: LayoutInflater = LayoutInflater.from(context)
    private var semdetail = emptyList<Semester>() // Cached copy of user

    constructor(context: Context, semdetail: ArrayList<Semester>) : this(context) {
        this.inflater = LayoutInflater.from(context)
        this.semdetail = semdetail
    }

    inner class SemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val textViewSem: TextView = itemView.findViewById(R.id.textViewSemDisplay)
        val textViewDate: TextView = itemView.findViewById(R.id.textViewSemDateDisplay)
        val textViewDetails: TextView = itemView.findViewById(R.id.textViewSemAmountDisplay)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SemViewHolder {
        val itemView = inflater.inflate(R.layout.semester_detail, parent, false)
        return SemViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: SemViewHolder, position: Int) {
        val current = semdetail[position]
        holder.textViewSem.text = current.semester
        holder.textViewDate.text = current.sem_date
        holder.textViewDetails.text = current.sem_loan_amount.toString()
    }

    override fun getItemCount() = semdetail.size
}