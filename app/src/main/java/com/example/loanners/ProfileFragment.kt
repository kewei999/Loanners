package com.example.loanners

import android.content.Context
import android.content.SharedPreferences
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Activities that contain this fragment must implement the
 * [ProfileFragment.OnFragmentInteractionListener] interface
 * to handle interaction events.
 * Use the [ProfileFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class ProfileFragment : Fragment() {
    var prefs: SharedPreferences? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.activity_profile, container, false)
        val btnProfile = view.findViewById(R.id.buttonProfileDetail) as Button
        val btnBanAcc = view.findViewById(R.id.buttonBankAcc) as Button
        val userName=view.findViewById(R.id.textViewuserName)as TextView
        val btnMangeAcc = view.findViewById(R.id.buttonManageAcc)as Button
        prefs = getActivity()?.getSharedPreferences("User", Context.MODE_PRIVATE)
        val studentName=prefs?.getString("student_name","DEFAULT")

        userName.setText(studentName)

        btnProfile.setOnClickListener {
            val fr = fragmentManager!!.beginTransaction()
            fr.replace(R.id.frameContainer2, profileDetailFragment())
            fr.addToBackStack(null)
            fr.commit()
        }

        btnBanAcc.setOnClickListener {
            val fr = fragmentManager!!.beginTransaction()
            fr.replace(R.id.frameContainer2, BankAccFragment())
            fr.addToBackStack(null)
            fr.commit()
        }

        btnMangeAcc.setOnClickListener{
            val fr = fragmentManager!!.beginTransaction()
            fr.replace(R.id.frameContainer2, ManageAccFragment())
            fr.addToBackStack(null)
            fr.commit()
        }

        return view
    }
}
