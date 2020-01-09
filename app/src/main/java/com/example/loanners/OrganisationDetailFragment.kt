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
 * [OrganisationDetailFragment.OnFragmentInteractionListener] interface
 * to handle interaction events.
 * Use the [OrganisationDetailFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class OrganisationDetailFragment : Fragment() {
    fun sendData(position:Int)
    {

    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.activity_organisation_detail, container, false)
    val btnApply=view.findViewById(R.id.buttonApply)as Button

        btnApply.setOnClickListener{
            val fr = fragmentManager!!.beginTransaction()
            fr.replace(R.id.frameOrgDetail, DialogFragment())
            fr.addToBackStack(null)
            fr.commit()

        }



        return view
    }



}
