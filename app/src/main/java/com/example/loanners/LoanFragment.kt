package com.example.loanners

import android.content.Context
import android.content.SharedPreferences
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.DefaultRetryPolicy
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import org.json.JSONArray
import org.json.JSONObject
import java.time.format.DateTimeFormatter

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Activities that contain this fragment must implement the
 * [LoanFragment.OnFragmentInteractionListener] interface
 * to handle interaction events.
 * Use the [LoanFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class LoanFragment : Fragment() {
    var prefs: SharedPreferences? = null
    lateinit var semList: ArrayList<Semester>
    lateinit var semAdapter: SemesterAdapter
    var email:String?=null
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
       val view= inflater.inflate(R.layout.activity_loan, container, false)
        prefs = getActivity()?.getSharedPreferences("loginPref", Context.MODE_PRIVATE)
        email=prefs?.getString("email","DEFAULT")
         semList = ArrayList()
        semAdapter = SemesterAdapter(view.context,semList)

        syncSem(view)
        return view
    }
    private fun syncSem(view: View){
        val url = getString(R.string.url_server) + getString(R.string.url_approval_loan)+getString(R.string.url_read)

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
                        for(i in 0 until size) {
                            var jsonUser: JSONObject = jsonArray.getJSONObject(i)
                            val studEmail = jsonUser .getString("student_email")
                            if (email == studEmail) {
                                val approvalloanID = jsonUser.getString("approval_loan_id")
                                val loanID = jsonUser.getString("loan_id")
                                val approvalDate=view.findViewById(R.id.textViewLoanTakenDisplay)as TextView
                                approvalDate.text=jsonUser.getString("date_approved").substring(0,11)
                                getSem(approvalloanID, view)
                                getLoanDetail(loanID,view)
                                 }
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

        //Volley request policy, only one time request
        jsonObjectRequest.retryPolicy = DefaultRetryPolicy(
            DefaultRetryPolicy.DEFAULT_TIMEOUT_MS,
            0, //no retry
            1f
        )

        // Access the RequestQueue through your singleton class.
        MySingleton.getInstance(view.context).addToRequestQueue(jsonObjectRequest)

    }

    private fun getSem(approvalloanID:String, view:View){
        val url = getString(R.string.url_server) +getString(R.string.url_semester) +getString(R.string.url_read)
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
                            val approvalLID = jsonUser.getString("approval_loan_id")
                            if(approvalLID==approvalloanID) {
                                val time= jsonUser.getString("sem_date").substring(0,11)

                                var sem = Semester(
                                    jsonUser.getString("approval_loan_id"),
                                    jsonUser.getDouble("sem_loan_amount"),
                                    time,
                                    jsonUser.getString("semester")
                                )
                                semList.add(sem)
                                semAdapter = SemesterAdapter(view.context, semList)
                            }

                        }
                        val semRecyclerView = view.findViewById<RecyclerView>(R.id.RecyclerViewSem)
                        semRecyclerView.adapter = semAdapter
                        semRecyclerView.layoutManager = LinearLayoutManager(view.context, LinearLayoutManager.VERTICAL, false)

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

    private fun getLoanDetail(loanID:String,view:View){
        val url = getString(R.string.url_server) + getString(R.string.url_loan)+getString(R.string.url_read)

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
                        for(i in 0 until size) {
                            var jsonUser: JSONObject = jsonArray.getJSONObject(i)
                            val LoanID = jsonUser .getString("loan_id")
                            if (loanID == LoanID) {
                                val loanterm = jsonUser.getString("loan_term")
                                val loanAmount=jsonUser.getString("loan_amount")
                                val loanInterest=jsonUser.getString("loan_interest")

                                val txtLoanTerm=view.findViewById(R.id.textViewtermLoanDisplay)as TextView
                                val txtLoanAmount=view.findViewById(R.id.textViewLoanAmountDIsplay)as TextView
                                val txtLoanInterest=view.findViewById(R.id.textViewLoanInterestDisplay)as TextView
                                txtLoanTerm.text=loanterm
                                txtLoanAmount.text="RM" + loanAmount
                                txtLoanInterest.text=loanInterest + "%"
                            }
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


