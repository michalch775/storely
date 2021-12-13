package com.example.smartrecordmobileapp.ui.dashboard

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.smartrecordmobileapp.R
import com.example.smartrecordmobileapp.databinding.FragmentRentalsBinding


//aktywność odpowiedzialna za wyświetlanie przedmiotów wypożyczonych przez użytkownika aplikacji

class RentalsFragment : Fragment() {

    private lateinit var myItemsViewModel: RentalsViewModel
    private var _binding: FragmentRentalsBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        myItemsViewModel =
            ViewModelProvider(this).get(RentalsViewModel::class.java)

        _binding = FragmentRentalsBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val textView: TextView = binding.textRentals
        myItemsViewModel.text.observe(viewLifecycleOwner, Observer {
            textView.text = it
        })
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}