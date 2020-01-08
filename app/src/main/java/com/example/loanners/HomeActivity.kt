package com.example.loanners

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.DefaultRetryPolicy
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.android.synthetic.main.activity_home.*
import kotlinx.android.synthetic.main.activity_loan.bottom_navigation
import org.json.JSONArray
import org.json.JSONObject


class HomeActivity: AppCompatActivity(){
    lateinit var latestLoanerList: ArrayList<Loaner>
    lateinit var mostLoanerList: ArrayList<Loaner>
    lateinit var mostAdapter: LoanerListAdapter
    lateinit var latestAdapter: LoanerListAdapter

    private val monNavigationItemSelectedListener= BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when (item.itemId) {
            R.id.nav_home -> {
                textViewHome.text=""
                replaceFragment(HomeFragment())
                return@OnNavigationItemSelectedListener true
            }

            R.id.nav_loan -> {
                textViewHome.text=""
                replaceFragment(LoanFragment())
                return@OnNavigationItemSelectedListener true
            }

            R.id.nav_profile -> {
                textViewHome.text=""
                replaceFragment(ProfileFragment())
                return@OnNavigationItemSelectedListener true
            }
            R.id.nav_list -> {
                textViewHome.text=""
                replaceFragment(ListFragment())
                return@OnNavigationItemSelectedListener true
            }

            else ->
                false
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        mostLoanerList = ArrayList()
        mostAdapter = LoanerListAdapter(this,mostLoanerList)
        latestLoanerList = ArrayList()
        latestAdapter = LoanerListAdapter(this,latestLoanerList)

       syncMostLoaner()
        syncLatestLoaner()

        bottom_navigation.setOnNavigationItemSelectedListener(monNavigationItemSelectedListener)
    }

    private fun replaceFragment(fragment: Fragment){
        val fragmentTransaction= supportFragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.fragmentContainer,fragment)
        fragmentTransaction.addToBackStack(null)
        fragmentTransaction.commit()
    }

    private fun syncMostLoaner(){
        val url = getString(R.string.url_server) + getString(R.string.url_loaner_read_most)

        val jsonObjectRequest = JsonObjectRequest(
            Request.Method.GET, url, null,
            Response.Listener { response ->
                // Process the JSON
                try{
                    if(response != null){
                        val strResponse = response.toString()
                        val jsonResponse  = JSONObject(strResponse)
                        val jsonArray: JSONArray = jsonResponse.getJSONArray("records")
                        val size: Int = jsonArray.length()

                        for(i in 0 until size){
                            var jsonUser: JSONObject = jsonArray.getJSONObject(i)
                            var loaner = Loaner(jsonUser.getString("loan_id"),jsonUser.getString("loan_name"),jsonUser.getString("loan_details")
                                ,jsonUser.getDouble("loan_amount").toFloat(),jsonUser.getDouble("loan_interest").toFloat(),jsonUser.getInt("loan_term"),
                                jsonUser.getInt("loan_count"), jsonUser.getString("announce_date"),jsonUser.getString("company_id"),R.drawable.building1)

                            mostLoanerList.add(loaner)
                            mostAdapter = LoanerListAdapter(this,mostLoanerList)

                        }
                        val mostRecyclerView = findViewById<RecyclerView>(R.id.mostRecyclerView)
                        mostRecyclerView.adapter = mostAdapter
                        mostRecyclerView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
                        Toast.makeText(applicationContext, "Record found :$size", Toast.LENGTH_LONG).show()
                    }
                }catch (e:Exception){
                    Log.d("Main", "Response: %s".format(e.message.toString()))

                }
            },
            Response.ErrorListener { error ->
                Log.d("Main", "Response: %s".format(error.message.toString()))
            }
        )

        //Volley request policy, only one time request
        jsonObjectRequest.retryPolicy = DefaultRetryPolicy(
            DefaultRetryPolicy.DEFAULT_TIMEOUT_MS,
            0, //no retry
            1f
        )

        // Access the RequestQueue through your singleton class.
        MySingleton.getInstance(this).addToRequestQueue(jsonObjectRequest)

    }

    private fun syncLatestLoaner() {
        val url = getString(R.string.url_server) + getString(R.string.url_loaner_read_latest)

        val jsonObjectRequest = JsonObjectRequest(
            Request.Method.GET, url, null,
            Response.Listener { response ->
                // Process the JSON
                try{
                    if(response != null){
                        val strResponse = response.toString()
                        val jsonResponse  = JSONObject(strResponse)
                        val jsonArray: JSONArray = jsonResponse.getJSONArray("records")
                        val size: Int = jsonArray.length()

                        for(i in 0 until size){
                            var jsonUser: JSONObject = jsonArray.getJSONObject(i)
                            var loaner = Loaner(jsonUser.getString("loan_id"),jsonUser.getString("loan_name"),jsonUser.getString("loan_details")
                                ,jsonUser.getDouble("loan_amount").toFloat(),jsonUser.getDouble("loan_interest").toFloat(),jsonUser.getInt("loan_term"),
                                jsonUser.getInt("loan_count"), jsonUser.getString("announce_date"),jsonUser.getString("company_id"),R.drawable.building1)

                            latestLoanerList.add(loaner)
                        }

                        val latestRecyclerView = findViewById<RecyclerView>(R.id.latestRecyclerView)
                        latestRecyclerView.adapter = latestAdapter
                        latestRecyclerView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
                        Toast.makeText(applicationContext, "Record found :$size", Toast.LENGTH_LONG).show()
                    }
                }catch (e:Exception){
                    Log.d("Main", "Response: %s".format(e.message.toString()))

                }
            },
            Response.ErrorListener { error ->
                Log.d("Main", "Response: %s".format(error.message.toString()))
            }
        )

        //Volley request policy, only one time request
        jsonObjectRequest.retryPolicy = DefaultRetryPolicy(
            DefaultRetryPolicy.DEFAULT_TIMEOUT_MS,
            0, //no retry
            1f
        )

        // Access the RequestQueue through your singleton class.
        MySingleton.getInstance(this).addToRequestQueue(jsonObjectRequest)
    }

}
