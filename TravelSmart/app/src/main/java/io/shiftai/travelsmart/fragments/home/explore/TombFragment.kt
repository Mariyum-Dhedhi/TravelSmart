package io.shiftai.travelsmart.fragments.home.explore

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import io.shiftai.travelsmart.R
import io.shiftai.travelsmart.databinding.FragmentTombBinding
import io.shiftai.travelsmart.util.hideBottomNavigationView
import io.shiftai.travelsmart.util.moveUpTextAnimation
import io.shiftai.travelsmart.util.verticalGradientText

class TombFragment : Fragment(R.layout.fragment_tomb) {
    private lateinit var binding: FragmentTombBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentTombBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.learnMore.setOnClickListener {
            findNavController().navigate(R.id.action_tombFragment_to_faisalFragment)
        }

        binding.back.setOnClickListener {
            findNavController().navigate(R.id.action_tombFragment_to_ramaFragment)
        }

        moveUpTextAnimation(binding.title)
        verticalGradientText(binding.title)
    }

    override fun onResume() {
        super.onResume()

        hideBottomNavigationView()
    }
}