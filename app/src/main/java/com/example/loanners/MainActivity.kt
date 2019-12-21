package com.example.loanners

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_sign_up.*
import kotlinx.android.synthetic.main.activity_main.editTextUserName as editTextUserName1
import kotlinx.android.synthetic.main.activity_sign_up.editTextPassword as editTextPassword1

class MainActivity : AppCompatActivity() {

    private var username = "user1"
    private var password = "abc123"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        buttonSignUp.setOnClickListener{
            callRegister()
        }

        buttonSignIn.setOnClickListener {
            signIn()
        }
    }

    private fun signIn() {
        val username = editTextUserName.text.toString()
        val password = editTextPassword.text.toString()
        if(username.equals(username)){
            if(password.equals(password)){
                Toast.makeText(this, "Log in successful", Toast.LENGTH_SHORT).show()
            }
            else{
                Toast.makeText(this, "Password wrong", Toast.LENGTH_SHORT).show()
            }
        }
        else{
            Toast.makeText(this, "Username wrong", Toast.LENGTH_SHORT).show()
        }
    }

    private fun callRegister(){

        val intent= Intent(this,SignUpActivity::class.java)
        startActivity(intent)
    }

}
