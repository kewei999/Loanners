package com.example.loanners

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.RadioButton
import android.widget.Toast
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import kotlinx.android.synthetic.main.activity_details.*
import org.json.JSONException
import org.json.JSONObject

class DetailsActivity : AppCompatActivity() {
    private lateinit var user:User
    private val educationName = arrayOf("Tunku Abdul Rahman University College","University of Cambridge","University of Malaya","University Tunku Abdul Rahman","Monash University")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_details)

        val email = intent.getStringExtra("EXTRA_EMAIL")
        readByEmail(email)

        var adapter = ArrayAdapter<String>(this, android.R.layout.select_dialog_item, educationName)
        val activity = autoEducation
        activity.threshold = 5//will start working from first character
        activity.setAdapter(adapter)//setting the adapter data into the AutoCompleteTextView

        buttonDone.setOnClickListener {
            validData()
        }
    }

    private fun validData() {
        val address = editTextStreet1.text.toString() + editTextStreet2.text.toString() + editTextCity.text.toString() + editTextState.text.toString() + editTextPost.text.toString()
        val fullName = editTextFullName.text.toString()
        val gender  = findViewById<RadioButton>(radioGender.checkedRadioButtonId).text.toString()

        val icNumber = editTextIC.text.toString()
        val icRegex = "/^\\d{12}\$/".toRegex()

        val phoneNumber = editTextPhoneNumber.text.toString()
        val phoneRegex = "^(01)[0-46-9]*[0-9]{7,8}\$".toRegex()

        val education = autoEducation.text.toString()
        val result = educationName.binarySearch(education,0)

        //if(result <= 0){
        //    val index = educationName.lastIndex
        //    educationName[index + 1] = education
        //}

        if(!icNumber.matches(icRegex)){
            Toast.makeText(this,"IC number didn't match format.", Toast.LENGTH_SHORT).show()
        }
        else if(!phoneNumber.matches(phoneRegex)){
            Toast.makeText(this,"Phone number didn't match format.",Toast.LENGTH_SHORT).show()
        }
        else {
            user = User(user.email, user.password, user.username, user.role, fullName, icNumber.toLong(), gender, phoneNumber.toLong(), address, education)
            updateUser(user)
        }
    }

    private fun updateUser(user: User) {
        val url = getString(R.string.url_server) + getString(R.string.url_user_update) + "?username=" + user.username +
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
                                Toast.makeText(applicationContext, "Register successful", Toast.LENGTH_LONG).show()
                                val intent = Intent(this, HomeActivity::class.java)
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

    private fun readByEmail(email:String) {
        val url = getString(R.string.url_server) + getString(R.string.url_user_read_by_email) + "?email=" + email

        // output = (TextView) findViewById(R.id.jsonData);
        val jsonObjectRequest = JsonObjectRequest(Request.Method.GET, url, null,
                Response.Listener { response ->
                    try {
                        if (response != null) {
                            val strResponse = response.toString()
                            val jsonResponse  = JSONObject(strResponse)

                            if(jsonResponse != null){
                                user = User(jsonResponse.getString("email"),jsonResponse.getString("password"),
                                        jsonResponse.getString("username"),jsonResponse.getString("role"),null,null,
                                        null,null,null,null)
                            }
                        }
                    } catch (e: JSONException) {
                        e.printStackTrace()
                    }
                },
                Response.ErrorListener { e ->
                    Log.e("Volley", "Error" + e.message.toString())
                }
        )
        // Access the RequestQueue through your singleton class.
        MySingleton.getInstance(this).addToRequestQueue(jsonObjectRequest)
    }
}