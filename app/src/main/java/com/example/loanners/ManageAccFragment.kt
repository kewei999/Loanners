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
 * [ManageAccFragment.OnFragmentInteractionListener] interface
 * to handle interaction events.
 * Use the [ManageAccFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class ManageAccFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.activity_manage_acc, container, false)
        val btnVerify = view.findViewById(R.id.buttonVerify) as Button
        val btnBack =view.findViewById(R.id.buttonBackProfile)as Button
        val btnChgPass=view.findViewById(R.id.buttonChangePassword)as Button

        btnVerify.setOnClickListener {

        }

        btnChgPass.setOnClickListener{
            activity!!.supportFragmentManager.beginTransaction()
                .replace(R.id.frameManageAcc, ChangePassFragment()).commit()
        }

        btnBack.setOnClickListener{
            activity!!.supportFragmentManager.beginTransaction()
                .replace(R.id.frameManageAcc, ProfileFragment()).commit()
        }

        return view
    }
}
