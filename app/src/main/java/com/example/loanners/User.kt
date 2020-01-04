package com.example.loanners

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName ="user")
data class User(@PrimaryKey val email: String,
                val password: String,
                val username: String,
                val role: String,
                val name: String?,
                val icNumber: Long?,
                val gender: String?,
                val phoneNo:Long?,
                val address: String?,
                val education: String?) {
}