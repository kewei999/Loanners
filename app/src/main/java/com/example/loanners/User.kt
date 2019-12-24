package com.example.loanners

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "User")
class User(@PrimaryKey val id: String = "U1001") {
    val userName:String = "user1"
    val password:String = "aaaa1111"
}