package io.shiftai.travelsmart.adaptors

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter

class HomeViewPagerAdaptor(
    private val filters: List<Fragment>,
    fm: FragmentManager,
    lifecycle: Lifecycle
): FragmentStateAdapter(fm, lifecycle) {

    override fun getItemCount(): Int {
        return filters.size
    }

    override fun createFragment(position: Int): Fragment {
        return filters[position]
    }
}