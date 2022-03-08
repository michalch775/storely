package com.example.smartrecordmobileapp.ui.scanning

import android.content.pm.PackageManager
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.view.WindowCompat
import androidx.fragment.app.FragmentActivity
import androidx.navigation.findNavController
import com.example.smartrecordmobileapp.R
import com.example.smartrecordmobileapp.R.id.nav_host_fragment_activity_main
import com.budiyev.android.codescanner.AutoFocusMode
import com.budiyev.android.codescanner.CodeScanner
import com.budiyev.android.codescanner.CodeScannerView
import com.budiyev.android.codescanner.DecodeCallback
import com.budiyev.android.codescanner.ErrorCallback
import com.budiyev.android.codescanner.ScanMode
import com.example.smartrecordmobileapp.databinding.FragmentScanningBinding
import com.google.android.material.bottomnavigation.BottomNavigationView


private const val CAMERA_REQUEST_CODE = 101

class ScanningFragment : Fragment() {

    private lateinit var viewModel: ScanningViewModel
    private var _binding: FragmentScanningBinding? = null

    private val binding get() = _binding!!

    private lateinit var codeScanner: CodeScanner

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        //setupPermissions()

        _binding = FragmentScanningBinding.inflate(inflater, container, false)
        val view = binding.root



        codeScanner()



        //val navController = binding.root.findNavController(R.id.nav_host_fragment_activity_main)

        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(ScanningViewModel::class.java)
        // TODO: Use the ViewModel
    }

    private fun codeScanner() {
        val scanView: CodeScannerView = binding.scanView

        codeScanner = CodeScanner(requireContext(), scanView)

        codeScanner.apply {
            camera = CodeScanner.CAMERA_BACK
            formats = CodeScanner.ALL_FORMATS

            autoFocusMode = AutoFocusMode.SAFE
            scanMode = ScanMode.SINGLE
            isAutoFocusEnabled = true
            isFlashEnabled = false

            decodeCallback = DecodeCallback {
                val tv_textView: TextView = binding.tvTextView

                tv_textView.text = it.text
            }

            errorCallback = ErrorCallback {
                Log.e("Scan", "Camera initialization error: ${it.message}")
            }
        }

        scanView.setOnClickListener {
            codeScanner.startPreview()
        }
    }

//    override fun onResume() {
//        super.onResume()
//        codeScanner.startPreview()
//    }
//
//    override fun onPause() {
//        codeScanner.releaseResources()
//        super.onPause()
//    }
//
//    private fun setupPermissions() {
//
//        val requestPermissionLauncher =
//            registerForActivityResult(
//                ActivityResultContracts.RequestPermission()
//            ) { isGranted: Boolean ->
//                if (isGranted) {
//                    // Permission is granted. Continue the action or workflow in your
//                    // app.
//                } else {
//                    // Explain to the user that the feature is unavailable because the
//                    // features requires a permission that the user has denied. At the
//                    // same time, respect the user's decision. Don't link to system
//                    // settings in an effort to convince the user to change their
//                    // decision.
//                }
//            }
//    }
//
//    private fun makeRequest() {
//        //ActivityCompat.requestPermissions(this, arrayOf(android.Manifest.permission.CAMERA), CAMERA_REQUEST_CODE)
//        requestPermissions(arrayOf(android.Manifest.permission.CAMERA), CAMERA_REQUEST_CODE);
//    }
//
//    override fun onRequestPermissionsResult(
//        requestCode: Int,
//        permissions: Array<out String>,
//        grantResults: IntArray
//    ) {
//        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
//
//        when (requestCode){
//            CAMERA_REQUEST_CODE -> {
//                if (grantResults.isEmpty() || grantResults[0] != PackageManager.PERMISSION_GRANTED) {
//                    Toast.makeText(this, "You need the camera permission to be able to use this app!", Toast.LENGTH_SHORT).show()
//                } else {
//                    //successful
//                }
//            }
//        }
//
//    }
//

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}