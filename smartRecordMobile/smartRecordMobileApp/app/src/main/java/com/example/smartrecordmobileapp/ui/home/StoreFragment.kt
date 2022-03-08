package com.example.smartrecordmobileapp.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import com.example.smartrecordmobileapp.R
import com.example.smartrecordmobileapp.databinding.FragmentStoreBinding

class StoreFragment : Fragment() {

    private lateinit var storeViewModel: StoreViewModel
    private var _binding: FragmentStoreBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        storeViewModel =
            ViewModelProvider(this).get(StoreViewModel::class.java)

        _binding = FragmentStoreBinding.inflate(inflater, container, false)
        val root: View = binding.root

        //setup navigation from store to scanning
        val btnGoToScan: Button = binding.btnGoToScan
        btnGoToScan.setOnClickListener { Navigation.findNavController(binding.root).navigate(R.id.action_navigation_store_to_navigation_scanning) }


        val textView: TextView = binding.textStore
        storeViewModel.text.observe(viewLifecycleOwner, Observer {
            textView.text = it
        })
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}