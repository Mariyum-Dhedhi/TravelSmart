package io.shiftai.travelsmart.fragments.home.explore

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import io.shiftai.travelsmart.R
import io.shiftai.travelsmart.databinding.FragmentRamaBinding
import io.shiftai.travelsmart.util.hideBottomNavigationView
import io.shiftai.travelsmart.util.moveUpTextAnimation
import io.shiftai.travelsmart.util.verticalGradientText

class RamaFragment : Fragment(R.layout.fragment_rama) {
    private lateinit var binding: FragmentRamaBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentRamaBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        binding.learnMore.setOnClickListener {
            findNavController().navigate(R.id.action_ramaFragment_to_tombFragment)
        }
        binding.back.setOnClickListener {
            findNavController().navigate(R.id.action_ramaFragment_to_exploreFragment)
        }

        moveUpTextAnimation(binding.title)
        verticalGradientText(binding.title)
    }

    override fun onResume() {
        super.onResume()

        hideBottomNavigationView()
    }
}