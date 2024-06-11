package io.shiftai.travelsmart.fragments.filters

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import io.shiftai.travelsmart.R
import io.shiftai.travelsmart.adaptors.BaseFilterAdaptor
import io.shiftai.travelsmart.databinding.FragmentBaseFilterBinding
import io.shiftai.travelsmart.util.showBottomNavigationView

open class BaseFilter : Fragment(R.layout.fragment_base_filter) {
    private lateinit var binding: FragmentBaseFilterBinding
    protected val  plansAdapter: BaseFilterAdaptor by lazy { BaseFilterAdaptor() }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentBaseFilterBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupPlans()

        plansAdapter.onClick = {
            val b = Bundle().apply { putParcelable("plan",it) }
            findNavController().navigate(R.id.action_homeFragment_to_planFragment,b)
        }
    }

    private fun setupPlans() {
        binding.rvPlans.apply {
            layoutManager =
                GridLayoutManager(requireContext(), 2, GridLayoutManager.VERTICAL,false)
            adapter = plansAdapter
        }
    }

    fun showLoading(){
        binding.progressbar.visibility = View.VISIBLE
    }

    fun hideLoading(){
        binding.progressbar.visibility = View.GONE
    }


    override fun onResume() {
        super.onResume()
        showBottomNavigationView()
    }

}