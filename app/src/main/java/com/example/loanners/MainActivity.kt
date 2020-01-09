package com.example.loanners

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.text.method.PasswordTransformationMethod
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_main.editTextPassword
import kotlinx.android.synthetic.main.activity_main.editTextUserName
import kotlinx.android.synthetic.main.activity_sign_up.*
import org.json.JSONException
import org.json.JSONObject


class MainActivity : AppCompatActivity() {
   lateinit var loginPreferences: SharedPreferences
    lateinit var loginPreferencesEditor: SharedPreferences.Editor
    var saveLogin: Boolean = false


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        editTextPassword.transformationMethod = PasswordTransformationMethod()

        loginPreferences = getSharedPreferences("loginPref", Context.MODE_PRIVATE)
        loginPreferencesEditor = loginPreferences.edit()

        if(loginPreferences.contains("username") && loginPreferences.contains("password") && radioButtonRememberMe.isChecked == true){
            val intent = Intent(this, HomeActivity::class.java)
            startActivity(intent)
            finish()
        }

        saveLogin = loginPreferences.getBoolean("saveLogin",false)
        if (saveLogin) {
            editTextUserName.setText(loginPreferences.getString("username", ""))
            editTextPassword.setText(loginPreferences.getString("password", ""))
            radioButtonRememberMe.isChecked = true
        }

        buttonSignUp.setOnClickListener {
            callRegister()
        }

        buttonSignIn.setOnClickListener {
            val username = editTextUserName.text.toString()
            val password = editTextPassword.text.toString()
            readOne(username,password)
        }
    }

    private fun callRegister() {
        val intent = Intent(this, SignUpActivity::class.java)
        startActivity(intent)
    }
private fun call(){
    Toast.makeText(this, "Log in successful", Toast.LENGTH_SHORT).show()
    val intent = Intent(this,HomeActivity::class.java)
    startActivity(intent)
}

    private fun readOne(username:String,password:String) {
        val loginURL = getString(R.string.url_server) + getString(R.string.url_student)+ getString(R.string.url_read_one) + "?username=" + username + "&password=" + password

        // output = (TextView) findViewById(R.id.jsonData);
        val jsonObjectRequest = JsonObjectRequest(Request.Method.GET, loginURL, null,
            Response.Listener { response ->
                try {
                    if (response != null) {
                        val strResponse = response.toString()
                        val jsonResponse  = JSONObject(strResponse)
                       val studentName = jsonResponse.getString("student_name")
                        val studentEmail = jsonResponse.getString("student_email")
                       // val verifyStatus=jsonResponse.getInt("student_verify_status")
                        Toast.makeText(this, "Log in successful", Toast.LENGTH_SHORT).show()
                        val intent = Intent(this, HomeActivity::class.java)

                        loginPreferencesEditor.putString("username", username)
                        loginPreferencesEditor.putString("password", password)
                        loginPreferencesEditor.putString("email",studentEmail)
                        loginPreferencesEditor.putString("studentName",studentName)
                        //loginPreferencesEditor.putInt("verifyStatus",verifyStatus)
                        loginPreferencesEditor.commit()

                        if (radioButtonRememberMe.isChecked) {
                            loginPreferencesEditor.putBoolean("saveLogin", true)
                            loginPreferencesEditor.commit()
                        }
                        startActivity(intent)
                        finish()

                    }
                } catch (e: JSONException) {
                    e.printStackTrace()
                }
            },
            Response.ErrorListener { e ->
                Log.e("Volley", "Error" + e.message.toString())
                Toast.makeText(this, "Username or password wrong", Toast.LENGTH_SHORT).show()
            }
        )
        // Access the RequestQueue through your singleton class.
        MySingleton.getInstance(this).addToRequestQueue(jsonObjectRequest)
    }
}