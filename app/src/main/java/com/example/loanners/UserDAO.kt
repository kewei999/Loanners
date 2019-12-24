package com.example.loanners

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

@Dao
interface UserDAO {
    @Insert
    suspend fun insertUser(user: User)

    @Query("SELECT * FROM user")
    fun getAll(): List<User>
    @Query("SELECT * FROM user WHERE id IN (:userIds)")
    fun loadAllByIds(userIds: IntArray): List<User>
    @Query("SELECT * FROM user WHERE userName LIKE :name AND " +

            "password LIKE :pass LIMIT 1")

    fun findByName(first: String, last: String): User
    @Insert
    fun insertAll(vararg users: User)
    @Delete
    fun delete(user: User)
}