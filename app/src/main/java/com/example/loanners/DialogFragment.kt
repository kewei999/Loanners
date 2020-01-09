package com.example.loanners

import android.content.Context
import android.content.Intent
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
 * [DialogFragment.OnFragmentInteractionListener] interface
 * to handle interaction events.
 * Use the [DialogFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class DialogFragment : Fragment() {
    private val PICKFILE_REQUEST_CODE = 1

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.activity_dialog, container, false)
        val btnPDF=view.findViewById(R.id.buttonPDF)as Button
        val btnDoc=view.findViewById(R.id.button2Document)as Button
        val btnBack : Button = view.findViewById(R.id.buttonBack)

        btnBack.setOnClickListener{
            activity!!.supportFragmentManager.beginTransaction()
                .replace(R.id.frameVerifyAcc, OrganisationDetailFragment()).commit()
        }

        btnPDF.setOnClickListener{
            var packageManager = view.context.getPackageManager()
            var launchIntent = packageManager.getLaunchIntentForPackage("com.intsig.camscanner")
            if(launchIntent != null){
                startActivity(launchIntent)
            }
            else{
                launchIntent = Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=com.intsig.camscanner"))
                startActivity(launchIntent)
            }
        }

        btnDoc.setOnClickListener{
            val intent = Intent(Intent.ACTION_GET_CONTENT)
            intent.type = "file/*"
            startActivityForResult(intent, PICKFILE_REQUEST_CODE)
        }



        return view


    }
}
