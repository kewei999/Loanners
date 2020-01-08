package com.example.loanners

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import kotlinx.android.synthetic.main.activity_sign_up.*
import org.json.JSONObject

class SignUpActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)

        buttonRegister.setOnClickListener{
            signUp()
        }
    }

    private fun signUp() {
        val email = editTextEmail.text.toString()
        val emailRegex = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+".toRegex()

        val username = editTextUserName.text.toString()
        val password = editTextPassword.text.toString()
        val confirmPassword = editTextconfirmPassword.text.toString()
        val type = spinnerUserType.selectedItem.toString()

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
            val user = User(email,password,username,type,null, null, null, null,null, null)
            createUser(user)
        }
    }

    private fun createUser(user: User) {
        val url = getString(R.string.url_server) + getString(R.string.url_user_create) + "?username=" + user.username +
                "&password=" + user.password + "&email=" + user.email + "&role=" + user.role + "&name=" + user.name +
                "&ic_number=" + user.icNumber + "&gender=" + user.gender + "&phone_no=" + user.phoneNo + "&address=" +
                user.address + "&education=" + user.education

        val jsonObjectRequest = JsonObjectRequest(
                Request.Method.GET, url, null,
                Response.Listener { response ->
                    // Process the JSON
                    try{
                        if(response != null){
                            val strResponse = response.toString()
                            val jsonResponse  = JSONObject(strResponse)
                            val success: String = jsonResponse.get("success").toString()

                            if(success.equals("1")){
                                val intent = Intent(this,DetailsActivity::class.java)
                                intent.putExtra("EXTRA_EMAIL",user.email)
                                startActivity(intent)
                            }else{
                                Toast.makeText(applicationContext, "Record not saved", Toast.LENGTH_LONG).show()
                            }
                        }
                    }catch (e:Exception){
                        Log.d("Main", "Response: %s".format(e.message.toString()))

                    }
                },
                Response.ErrorListener { error ->
                    Log.d("Main", "Response: %s".format(error.message.toString()))
                }
        )

        // Access the RequestQueue through your singleton class.
        MySingleton.getInstance(this).addToRequestQueue(jsonObjectRequest)

    }

}