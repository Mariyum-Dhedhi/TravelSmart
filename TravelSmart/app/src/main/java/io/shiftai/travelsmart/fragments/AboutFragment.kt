package io.shiftai.travelsmart.fragments

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
import io.shiftai.travelsmart.databinding.FragmentAboutBinding
import io.shiftai.travelsmart.databinding.FragmentExploreBinding
import io.shiftai.travelsmart.util.hideBottomNavigationView
import io.shiftai.travelsmart.util.verticalGradientText

class AboutFragment : Fragment(R.layout.fragment_about) {
    private lateinit var binding: FragmentAboutBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAboutBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.back.setOnClickListener {
            findNavController().navigate(R.id.action_aboutFragment_to_profileFragment)
        }
    }

    override fun onResume() {
        super.onResume()

        hideBottomNavigationView()
    }
}