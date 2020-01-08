package com.example.loanners

import android.media.Image
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.temporal.TemporalAmount
import java.util.*

@Entity(tableName = "loan")
class Loaner (@PrimaryKey  val loanID: String,
              val loanName: String,
              val loanDetails: String,
              val loanAmount: Float,
              val loanInterest: Float,
              val loanTerm: Int,
              val loanCount: Int,
              val announceDate: String,
              val companyID : String,
              val image: Int)