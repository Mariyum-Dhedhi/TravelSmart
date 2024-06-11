package io.shiftai.travelsmart.fragments.loginSignup

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import io.shiftai.travelsmart.R
import io.shiftai.travelsmart.data.User
import io.shiftai.travelsmart.databinding.FragmentSignupBinding
import io.shiftai.travelsmart.util.Resource
import io.shiftai.travelsmart.util.SignupValidation
import io.shiftai.travelsmart.viewModels.SignInViewModel
import io.shiftai.travelsmart.viewModels.SignUpViewModel
import kotlinx.coroutines.Dispatchers

import kotlinx.coroutines.withContext

private val TAG = "SignUpFragment"
@AndroidEntryPoint
class SignupFragment : Fragment(R.layout.fragment_signup) {

    private lateinit var binding: FragmentSignupBinding
    private val viewModel by viewModels<SignUpViewModel>()
    private val viewModelSignIn by viewModels<SignInViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSignupBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.signIn.setOnClickListener {
            findNavController().navigate(R.id.action_signupFragment_to_signinFragment)
        }

        binding.apply {
            bSignUp.setOnClickListener {
                val user = User(
                    edUserName.text.toString().trim(),
                    edPassword.text.toString().trim(),
                    edEmail.text.toString().trim(),
                    edContact.text.toString().trim()
                )
                val password = edPassword.text.toString()
                viewModel.createAccountWithEmailAndPassword(user, password)
            }
        }


        lifecycleScope.launchWhenStarted {
            viewModel.signup.collect {
                when (it) {
                    is Resource.Loading -> {
                        binding.bSignUp.startAnimation()
                    }
                    is Resource.Success -> {
                        Log.d("test",it.data.toString())
                        binding.bSignUp.revertAnimation()
                    }
                    is Resource.Error -> {
                        Log.e(io.shiftai.travelsmart.fragments.loginSignup.TAG,it.message.toString())
                        binding.bSignUp.revertAnimation()
                    }
                    else -> Unit
                }
            }
        }
        lifecycleScope.launchWhenStarted {
            viewModel.validation.collect { validation ->
                if (validation.email is SignupValidation.Failed) {
                    withContext(Dispatchers.Main) {
                        binding.edEmail.apply {
                            requestFocus()
                            error = validation.email.message
                        }
                    }
                }
                if (validation.password is SignupValidation.Failed) {
                    withContext(Dispatchers.Main) {
                        binding.edPassword.apply {
                            requestFocus()
                            error = validation.password.message
                        }
                    }
                }
            }
        }
    }
}
