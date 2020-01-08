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
 * [profileDetailFragment.OnFragmentInteractionListener] interface
 * to handle interaction events.
 * Use the [profileDetailFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class profileDetailFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.activity_personal_detail, container, false)
        val btnBack : Button = view.findViewById(R.id.buttonBack)

        btnBack.setOnClickListener{
            activity!!.supportFragmentManager.beginTransaction()
                .replace(R.id.framePersonalDetail, ProfileFragment()).commit()
        }
        return view
    }


}
