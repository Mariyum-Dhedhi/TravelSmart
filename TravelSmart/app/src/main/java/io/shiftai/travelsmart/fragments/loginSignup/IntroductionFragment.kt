package io.shiftai.travelsmart.fragments.loginSignup

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import io.shiftai.travelsmart.R
import io.shiftai.travelsmart.activities.HomeActivity
import io.shiftai.travelsmart.activities.SplashActivity
import io.shiftai.travelsmart.databinding.FragmentIntroductionBinding
import io.shiftai.travelsmart.viewModels.IntroductionViewModel
import io.shiftai.travelsmart.viewModels.IntroductionViewModel.Companion.HOME_ACTIVITY
import io.shiftai.travelsmart.viewModels.IntroductionViewModel.Companion.SIGN_IN_FRAGMENT

@AndroidEntryPoint
class IntroductionFragment : Fragment(R.layout.fragment_introduction) {
    private lateinit var binding: FragmentIntroductionBinding
    private val viewModel by viewModels<IntroductionViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentIntroductionBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        lifecycleScope.launchWhenStarted {
            viewModel.navigate.collect {
                when (it) {
                    HOME_ACTIVITY -> {
                        Intent(requireActivity(), HomeActivity::class.java).also { intent ->
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
                            startActivity(intent)
                        }
                    }

                    SIGN_IN_FRAGMENT -> {
                        findNavController().navigate(it)
                    }

                    else -> Unit
                }
            }
        }

        binding.nextButton.setOnClickListener {
            viewModel.startButtonClick()
            findNavController().navigate(R.id.action_introductionFragment_to_signupFragment)
        }
    }
}