package io.shiftai.travelsmart.fragments.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.google.android.material.tabs.TabLayoutMediator
import dagger.hilt.android.AndroidEntryPoint
import io.shiftai.travelsmart.R
import io.shiftai.travelsmart.adaptors.HomeViewPagerAdaptor
import io.shiftai.travelsmart.databinding.FragmentHomeBinding
import io.shiftai.travelsmart.fragments.filters.AllFilter
import io.shiftai.travelsmart.fragments.filters.LatestFilter
import io.shiftai.travelsmart.fragments.filters.PopularFilter
import io.shiftai.travelsmart.util.showBottomNavigationView

@AndroidEntryPoint
class HomeFragment : Fragment(R.layout.fragment_home) {
    private lateinit var binding: FragmentHomeBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.explorePlaces.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_exploreFragment)
        }

        val categories = arrayListOf<Fragment>(
            AllFilter(),
            LatestFilter(),
            PopularFilter()
        )

        val viewPager2Adapter =
            HomeViewPagerAdaptor(categories , childFragmentManager, lifecycle)
        binding.viewpagerHome.adapter = viewPager2Adapter
        TabLayoutMediator(binding.tabLayout, binding.viewpagerHome) { tab, position ->
            when (position) {
                0 -> tab.text = "All"
                1 -> tab.text = "Latest"
                2 -> tab.text = "Popular"
            }
        }.attach()
    }

    override fun onResume() {
        super.onResume()

        showBottomNavigationView()
    }
}
