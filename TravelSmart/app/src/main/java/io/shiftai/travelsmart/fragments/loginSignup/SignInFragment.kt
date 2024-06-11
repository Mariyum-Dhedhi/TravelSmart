package io.shiftai.travelsmart.fragments.loginSignup

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import io.shiftai.travelsmart.activities.HomeActivity
import io.shiftai.travelsmart.R
import io.shiftai.travelsmart.databinding.FragmentSigninBinding
import io.shiftai.travelsmart.dialog.setupBottomSheetDialog
import io.shiftai.travelsmart.util.Resource
import io.shiftai.travelsmart.viewModels.SignInViewModel

@AndroidEntryPoint
class SignInFragment : Fragment(R.layout.fragment_signin) {
    private lateinit var binding: FragmentSigninBinding
    private val viewModel by viewModels<SignInViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSigninBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.signUp.setOnClickListener {
            findNavController().navigate(R.id.action_signinFragment_to_signupFragment)
        }

        binding.apply {
            bSignIn.setOnClickListener {
                val email = edEmail.text.toString().trim()
                val password = edPassword.text.toString()
                viewModel.login(email, password)
            }
        }
        binding.forgotPassword.setOnClickListener {
            setupBottomSheetDialog { email ->
                viewModel.resetPassword(email)
            }
        }

        lifecycleScope.launchWhenStarted {
            viewModel.resetPassword.collect{
                when (it) {
                    is Resource.Loading -> {
                    }
                    is Resource.Success -> {
                        Snackbar.make(requireView(),"Reset link was sent to your email!", Snackbar.LENGTH_LONG).show()
                    }
                    is Resource.Error -> {
                        Snackbar.make(requireView(),"Error: ${it.message}", Snackbar.LENGTH_LONG).show()
                    }
                    else -> Unit

                }
            }
        }
        lifecycleScope.launchWhenStarted {
            viewModel.login.collect {
                when (it) {
                    is Resource.Loading -> {
                        binding.bSignIn.startAnimation()
                    }
                    is Resource.Success -> {
                        binding.bSignIn.revertAnimation()
                        Intent(requireActivity(), HomeActivity::class.java).also { intent ->
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
                            startActivity(intent)
                        }
                    }
                    is Resource.Error -> {
                        Toast.makeText(requireContext(), it.message, Toast.LENGTH_LONG).show()
                        binding.bSignIn.revertAnimation()
                    }
                    else -> Unit

                }
            }
        }
    }
}