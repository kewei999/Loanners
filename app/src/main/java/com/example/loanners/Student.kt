package com.example.loanners

import android.media.Image
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName ="student")
data class Student(@PrimaryKey val email: String,
                   val password: String,
                   val username: String,
                   val image: Image?,
                   val name: String?,
                   val icNumber: Long?,
                   val gender: String?,
                   val phoneNo:Long?,
                   val address: String?,
                   val education: String?,
                    val bankAccount:String?,
                   val cgpa:Double,
                   val verify_status:Int,
                  val loanStatus:Int)
