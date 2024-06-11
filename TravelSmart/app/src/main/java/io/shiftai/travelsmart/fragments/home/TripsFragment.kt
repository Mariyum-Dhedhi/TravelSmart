package io.shiftai.travelsmart.fragments.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isGone
import androidx.navigation.fragment.findNavController
import com.google.android.material.bottomappbar.BottomAppBar
import com.google.android.material.floatingactionbutton.FloatingActionButton
import io.shiftai.travelsmart.R
import io.shiftai.travelsmart.activities.HomeActivity
import io.shiftai.travelsmart.databinding.FragmentExploreBinding
import io.shiftai.travelsmart.databinding.FragmentTripsBinding
import io.shiftai.travelsmart.util.hideBottomNavigationView
import io.shiftai.travelsmart.util.showBottomNavigationView


class TripsFragment : Fragment(R.layout.fragment_trips) {
    private lateinit var binding: FragmentTripsBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentTripsBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.planJourney.setOnClickListener {
            findNavController().navigate(R.id.action_tripsFragment_to_datesFragment)
        }
    }

    override fun onResume() {
        super.onResume()

        hideBottomNavigationView()
    }
}