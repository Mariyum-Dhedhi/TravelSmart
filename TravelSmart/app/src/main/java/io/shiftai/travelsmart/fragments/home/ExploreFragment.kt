package io.shiftai.travelsmart.fragments.home

import android.graphics.Color
import android.graphics.LinearGradient
import android.graphics.Shader
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.text.TextPaint
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import io.shiftai.travelsmart.R
import io.shiftai.travelsmart.databinding.FragmentExploreBinding
import io.shiftai.travelsmart.util.hideBottomNavigationView
import io.shiftai.travelsmart.util.verticalGradientText

class ExploreFragment : Fragment(R.layout.fragment_explore) {
    private lateinit var binding: FragmentExploreBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentExploreBinding.inflate(inflater)
        return binding.root
    }

    private val handler = Handler(Looper.getMainLooper())

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.learnMore.setOnClickListener {
            findNavController().navigate(R.id.action_exploreFragment_to_ramaFragment)
        }
        binding.back.setOnClickListener {
            findNavController().navigate(R.id.action_exploreFragment_to_homeFragment)
        }

        verticalGradientText(binding.title)

    }

    override fun onResume() {
        super.onResume()

        hideBottomNavigationView()
    }
}