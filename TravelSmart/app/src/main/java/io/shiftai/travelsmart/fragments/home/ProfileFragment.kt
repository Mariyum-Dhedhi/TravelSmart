package io.shiftai.travelsmart.fragments.home

import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import io.shiftai.travelsmart.data.User
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import dagger.hilt.android.AndroidEntryPoint
import io.shiftai.travelsmart.R
import io.shiftai.travelsmart.activities.SignInSignUpActivity
import io.shiftai.travelsmart.databinding.FragmentProfileBinding
import io.shiftai.travelsmart.util.Resource
import io.shiftai.travelsmart.util.showBottomNavigationView
import io.shiftai.travelsmart.viewModels.ProfileViewModel
import kotlinx.coroutines.flow.collectLatest

@AndroidEntryPoint
class ProfileFragment : Fragment(R.layout.fragment_profile) {
    private lateinit var binding: FragmentProfileBinding
    private val viewModel by viewModels<ProfileViewModel>()
    private var selectedImageUri: Uri? = null

    private val pickImage = registerForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
        uri?.let {
            selectedImageUri = it
            binding.imageUser.setImageURI(it)
            uploadImageToFirebase(it)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentProfileBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.about.setOnClickListener {
            findNavController().navigate(R.id.action_profileFragment_to_aboutFragment)
        }

        binding.back.setOnClickListener {
            findNavController().navigate(R.id.action_walletFragment_to_homeFragment)
        }

        binding.logout.setOnClickListener {
            viewModel.logout()
            val intent = Intent(requireActivity(), SignInSignUpActivity::class.java)
            startActivity(intent)
            requireActivity().finish()
        }

        binding.edit.setOnClickListener {
            pickImage.launch("image/*")
        }

        lifecycleScope.launchWhenStarted {
            viewModel.user.collectLatest {
                when (it) {
                    is Resource.Loading -> {
                    }
                    is Resource.Success -> {
                        Glide.with(requireView()).load(it.data!!.imagePath).error(
                            ColorDrawable(Color.parseColor("#808080"))
                        ).into(binding.imageUser)
                        binding.name.text = it.data.userName
                    }
                    is Resource.Error -> {
                        Toast.makeText(requireContext(), it.message, Toast.LENGTH_SHORT).show()
                    }
                    else -> Unit
                }
            }
        }
    }

    private fun uploadImageToFirebase(uri: Uri) {
        val storageReference = Firebase.storage.reference.child("user/${viewModel.auth.uid!!}")
        storageReference.putFile(uri).addOnSuccessListener {
            storageReference.downloadUrl.addOnSuccessListener { downloadUri ->
                viewModel.updateUserImage(downloadUri.toString())
            }
            Toast.makeText(requireContext(), "Image upload", Toast.LENGTH_SHORT).show()
        }.addOnFailureListener {
            Toast.makeText(requireContext(), "Image upload failed", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onResume() {
        super.onResume()
        showBottomNavigationView()
    }
}
