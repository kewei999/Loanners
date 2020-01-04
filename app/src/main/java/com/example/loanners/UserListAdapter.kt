package com.example.loanners

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView

class UserListAdapter internal constructor(context: Context) {
    private val inflater: LayoutInflater = LayoutInflater.from(context)
    private var users = emptyList<User>() // Cached copy of user

    internal fun setUsers(users: List<User>) {
        this.users = users
    }

    fun getItemCount() = users.size
}