package io.shiftai.travelsmart.fragments.home.plan

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import io.shiftai.travelsmart.R
import io.shiftai.travelsmart.adaptors.PlanAdaptor
import io.shiftai.travelsmart.data.Plan
import io.shiftai.travelsmart.databinding.FragmentPlanPagerBinding
import io.shiftai.travelsmart.util.Resource
import io.shiftai.travelsmart.viewModels.DayViewModel
import kotlinx.coroutines.flow.collectLatest

@AndroidEntryPoint
class PlanPagerFragment : Fragment(R.layout.fragment_plan_pager) {
    companion object {
        private const val ARG_PLAN = "plan"

        fun newInstance(plan: Plan): PlanPagerFragment {
            val fragment = PlanPagerFragment()
            val args = Bundle()
            args.putParcelable(ARG_PLAN, plan)
            fragment.arguments = args
            return fragment
        }
    }
    private lateinit var plan: Plan
    private lateinit var binding: FragmentPlanPagerBinding
    private lateinit var plansAdapter: PlanAdaptor
    private val viewModel by viewModels<DayViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentPlanPagerBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        plan = arguments?.getParcelable(ARG_PLAN)!!
        setupPlans()

        plansAdapter.onClick = {
            val bundle = Bundle().apply {
                putParcelable("plan", plan)
                putParcelable("day", it)
            }
            findNavController().navigate(R.id.action_planDetailsFragment_to_dayDetailsFragment,bundle)
        }

        lifecycleScope.launchWhenStarted {
            viewModel.day.collectLatest {
                when (it) {
                    is Resource.Loading -> {
                        showLoading()
                    }
                    is Resource.Success -> {
                        plansAdapter.differ.submitList(it.data)
                        hideLoading()
                    }
                    is Resource.Error -> {
                        Snackbar.make(requireView(), it.message.toString(), Snackbar.LENGTH_LONG)
                            .show()
                        hideLoading()
                    }
                    else -> Unit
                }
            }
        }
    }

    private fun setupPlans() {

        plansAdapter = PlanAdaptor()

        binding.rvPlans.apply {
            layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL,false)
            adapter = plansAdapter
        }
    }

    fun showLoading(){
        binding.progressbar.visibility = View.VISIBLE
    }

    fun hideLoading(){
        binding.progressbar.visibility = View.GONE
    }

}