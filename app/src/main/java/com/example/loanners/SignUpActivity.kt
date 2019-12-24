package com.example.loanners

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_sign_up.*
import java.util.regex.Pattern

class SignUpActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)

        buttonRegister.setOnClickListener{
                signUp()
        }
    }

    private fun signUp() {
        val email = editTextEmail.text.toString().trim()
        val emailRegex = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+".toRegex()

        val username = editTextUserName.text.toString()
        val password = editTextPassword.text.toString()
        val confirmPassword = editTextconfirmPassword.text.toString()
        //val type = spinnerUserType.selectedItem.toString()

        if(username == "" || email == "" ){
            Toast.makeText(this,"Form shouldn't left blank.",Toast.LENGTH_SHORT).show()
        }
        else if(!email.matches(emailRegex)){
            Toast.makeText(this,"Invalid email.",Toast.LENGTH_SHORT).show()
        }
        else if(password != "" && confirmPassword != password){
            Toast.makeText(this,"Those password didn't match. Try again.",Toast.LENGTH_SHORT).show()
        }
        else{
            val intent= Intent(this,DetailsActivity::class.java)
            startActivity(intent)
        }
    }

}
