package com.example.loanners

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Activities that contain this fragment must implement the
 * [ChangePassFragment.OnFragmentInteractionListener] interface
 * to handle interaction events.
 * Use the [ChangePassFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class ChangePassFragment : Fragment() {
    var prefs: SharedPreferences? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.activity_change_pass, container, false)
        val btnBack = view.findViewById(R.id.buttonBack)as Button
        val chgPass=view.findViewById(R.id.buttonconfirm)as Button
        val currentPass=view.findViewById(R.id.editTextEnterPass)as EditText
        val newpass=view.findViewById(R.id.editTextEnterNewPass)as EditText
        val confirmnewpass=view.findViewById(R.id.editTextconfirmnewpass)as EditText
        prefs = getActivity()?.getSharedPreferences("User", Context.MODE_PRIVATE)
        val username=prefs?.getString("username","DEFAULT")
        val password=prefs?.getString("password","DEFAULT")

        btnBack.setOnClickListener{
            activity!!.supportFragmentManager.beginTransaction()
                .replace(R.id.frameChgPass, ManageAccFragment()).commit()

        }
        chgPass.setOnClickListener{

            if(currentPass.text.toString()==password && username=="Sam9913"){
                if(newpass.text.toString()!=confirmnewpass.text.toString()){
                    Toast.makeText(getActivity(), "Those password didn't match. Try again.", Toast.LENGTH_SHORT).show()

                }
                else{
                    Toast.makeText(getActivity(), "Change Password Successfully.", Toast.LENGTH_SHORT).show()


                }
            }
        }

        return view
    }


    }

