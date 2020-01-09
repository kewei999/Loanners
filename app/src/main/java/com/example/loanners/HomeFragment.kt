package com.example.loanners

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.DefaultRetryPolicy
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import org.json.JSONArray
import org.json.JSONObject

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Activities that contain this fragment must implement the
 * [HomeFragment.OnFragmentInteractionListener] interface
 * to handle interaction events.
 * Use the [HomeFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class HomeFragment : Fragment() {
    lateinit var latestLoanerList: ArrayList<Loaner>
    lateinit var mostLoanerList: ArrayList<Loaner>
    lateinit var mostAdapter: LoanerListAdapter
    lateinit var latestAdapter: LoanerListAdapter


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.activity_home, container, false)
        mostLoanerList = ArrayList()
        mostAdapter = LoanerListAdapter(view.context,mostLoanerList)
        latestLoanerList = ArrayList()
        latestAdapter = LoanerListAdapter(view.context,latestLoanerList)

        syncMostLoaner(view)
        syncLatestLoaner(view)
        return view

    }

    private fun syncMostLoaner(view: View){
        val url = getString(R.string.url_server) + getString(R.string.url_loan)+getString(R.string.url_read_latest)

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
                            mostAdapter = LoanerListAdapter(view.context,mostLoanerList)

                        }
                        val mostRecyclerView = view.findViewById<RecyclerView>(R.id.mostRecyclerView)
                        mostRecyclerView.adapter = mostAdapter
                        mostRecyclerView.layoutManager = LinearLayoutManager(view.context, LinearLayoutManager.HORIZONTAL, false)
                        Toast.makeText(activity, "Record found :$size", Toast.LENGTH_LONG).show()
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
        MySingleton.getInstance(view.context).addToRequestQueue(jsonObjectRequest)

    }

    private fun syncLatestLoaner(view: View) {
        val url = getString(R.string.url_server) + getString(R.string.url_loan)+getString(R.string.url_read_latest)

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

                        val latestRecyclerView = view.findViewById<RecyclerView>(R.id.latestRecyclerView)
                        latestRecyclerView.adapter = latestAdapter
                        latestRecyclerView.layoutManager = LinearLayoutManager(view.context, LinearLayoutManager.HORIZONTAL, false)
                        Toast.makeText(activity, "Record found :$size", Toast.LENGTH_LONG).show()
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
        MySingleton.getInstance(view.context).addToRequestQueue(jsonObjectRequest)
    }
}

