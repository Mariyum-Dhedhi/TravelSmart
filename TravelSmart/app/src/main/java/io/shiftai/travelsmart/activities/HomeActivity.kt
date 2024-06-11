package io.shiftai.travelsmart.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import dagger.hilt.android.AndroidEntryPoint
import io.shiftai.travelsmart.R
import io.shiftai.travelsmart.databinding.ActivityHomeBinding
import kotlinx.coroutines.flow.collectLatest

@AndroidEntryPoint
class HomeActivity : BaseActivity() {

    val binding by lazy {
        ActivityHomeBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        val navController = findNavController(R.id.homeHostFragment)
        binding.bottomNavigation.setupWithNavController(navController)

        binding.AIButton.setOnClickListener {
            findNavController(R.id.homeHostFragment).navigate(R.id.tripsFragment)

            binding.bottomNavigation.visibility = View.GONE
            binding.AIButton.visibility = View.GONE
        }

    }
}