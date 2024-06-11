package io.shiftai.travelsmart.fragments.home.explore

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import io.shiftai.travelsmart.R
import io.shiftai.travelsmart.databinding.FragmentFaisalBinding
import io.shiftai.travelsmart.databinding.FragmentTombBinding
import io.shiftai.travelsmart.util.hideBottomNavigationView
import io.shiftai.travelsmart.util.moveUpTextAnimation
import io.shiftai.travelsmart.util.verticalGradientText

class FaisalFragment : Fragment(R.layout.fragment_tomb) {
    private lateinit var binding: FragmentFaisalBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentFaisalBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.explorePlans.setOnClickListener {
            findNavController().navigate(R.id.action_faisalFragment_to_homeFragment)
        }

        binding.back.setOnClickListener {
            findNavController().navigate(R.id.action_faisalFragment_to_tombFragment)
        }

        moveUpTextAnimation(binding.title)
        verticalGradientText(binding.title)
    }

    override fun onResume() {
        super.onResume()

        hideBottomNavigationView()
    }
}