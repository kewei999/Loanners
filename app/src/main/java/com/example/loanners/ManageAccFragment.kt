package com.example.loanners

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat

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
    private val CAMERA_REQUEST_CODE = 1
    private val REQUEST_GALLERY_CAMERA = 1
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.activity_manage_acc, container, false)
        val btnVerify = view.findViewById(R.id.buttonVerify) as Button
        val btnBack =view.findViewById(R.id.buttonBackProfile)as Button
        val btnChgPass=view.findViewById(R.id.buttonChangePassword)as Button
        val pref = activity!!.getSharedPreferences("loginPref", Context.MODE_PRIVATE)
        var verify = pref.getInt("verifyStatus",0)

        btnVerify.setOnClickListener{
            if(verify==0) {
                openCamera(view)
                Toast.makeText(activity, "Verify Done", Toast.LENGTH_LONG).show()
            }
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
    private fun openCamera(view:View) {
        var packageManager = view.context.getPackageManager()
        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        var launchIntent = packageManager.getLaunchIntentForPackage("com.intsig.camscanner")
        if(launchIntent != null){
            startActivity(launchIntent)
        }
        if (intent.resolveActivity(packageManager) != null)
            startActivityForResult(intent, CAMERA_REQUEST_CODE)
    }




}
