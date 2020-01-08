package com.example.loanners

import android.content.Context
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Activities that contain this fragment must implement the
 * [BankAccFragment.OnFragmentInteractionListener] interface
 * to handle interaction events.
 * Use the [BankAccFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class BankAccFragment : Fragment() {

override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
        ): View? {
            val view = inflater.inflate(R.layout.activity_bank_acc, container, false)
            val btnBack =view.findViewById(R.id.buttonBack1)as Button

            btnBack.setOnClickListener{
                activity!!.supportFragmentManager.beginTransaction()
                    .replace(R.id.frameBankAcc, ProfileFragment()).commit()
            }


            return view
        }


}
