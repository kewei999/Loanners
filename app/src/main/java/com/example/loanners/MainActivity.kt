package com.example.loanners

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_sign_up.*
import org.json.JSONException
import org.json.JSONObject
import kotlinx.android.synthetic.main.activity_main.editTextUserName as editTextUserName1
import kotlinx.android.synthetic.main.activity_sign_up.editTextPassword as editTextPassword1


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        buttonSignUp.setOnClickListener {
            callRegister()
        }

        buttonSignIn.setOnClickListener {
            val username = editTextUserName.text.toString()
            val password = editTextPassword.text.toString()
            readOne(username,password)
            //signIn()
        }
    }

    private fun callRegister() {
        val intent = Intent(this, SignUpActivity::class.java)
        startActivity(intent)
    }

    private fun readOne(username:String,password:String) {
        val loginURL = getString(R.string.url_server) + getString(R.string.url_user_read_one) + "?username=" + username + "&password=" + password

        // output = (TextView) findViewById(R.id.jsonData);
        val jsonObjectRequest = JsonObjectRequest(Request.Method.GET, loginURL, null,
                Response.Listener { response ->
                    try {
                        if (response != null) {
                            val strResponse = response.toString()
                            val jsonResponse  = JSONObject(strResponse)

                            if(jsonResponse != null){
                                Toast.makeText(this, "Log in successful", Toast.LENGTH_SHORT).show()
                                val intent = Intent(this, HomeActivity::class.java)
                                startActivity(intent)
                            }
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