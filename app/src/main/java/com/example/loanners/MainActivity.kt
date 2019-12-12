package com.example.loanners

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_sign_up.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        buttonSignUp.setOnClickListener{
            callRegister()
        }

    }

    private fun callRegister(){

        val intent= Intent(this,SignUpActivity::class.java)
        startActivity(intent)
    }

}
