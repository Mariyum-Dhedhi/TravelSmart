package io.shiftai.travelsmart.fragments.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import io.shiftai.travelsmart.R
import io.shiftai.travelsmart.databinding.FragmentMyTripsBinding

class MyTripsFragment : Fragment(R.layout.fragment_my_trips) {
    private lateinit var binding: FragmentMyTripsBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMyTripsBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.back.setOnClickListener {
            findNavController().navigate(R.id.action_myTripsFragment_to_homeFragment)
        }
    }

}