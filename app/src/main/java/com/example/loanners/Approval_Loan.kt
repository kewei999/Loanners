package com.example.loanners

import android.media.Image
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.temporal.TemporalAmount
import java.util.*

@Entity(tableName = "approval_loan")
class Approval_Loan (@PrimaryKey  val approval_loan_id: String,
                     val loan_status: String,
                     val date_apply: Date,
                     val date_approved: Date,
                     val loan_id: String,
                     val student_email: String
                   )