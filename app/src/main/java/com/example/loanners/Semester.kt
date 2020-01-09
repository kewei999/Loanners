package com.example.loanners

import android.media.Image
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import java.text.DecimalFormat
import java.time.temporal.TemporalAmount
import java.util.*

@Entity(tableName = "semester")
class Semester ( val approval_loan_id: String,
                val sem_loan_amount:Double,
                 val sem_date:String,
                 val semester:String)

