package io.shiftai.travelsmart.fragments.home.trips

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import io.shiftai.travelsmart.R
import io.shiftai.travelsmart.databinding.FragmentCategoryBinding
import io.shiftai.travelsmart.databinding.FragmentFinalizeBinding

class FinalizeFragment : Fragment(R.layout.fragment_finalize) {
    private lateinit var binding: FragmentFinalizeBinding
    private val args by navArgs<CategoryFragmentArgs>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentFinalizeBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val plan = args.plan

        binding.apply {
            destination.text = plan.destination
            startDate.text = plan.startDate
            endDate.text = plan.endDate
            daysNum.text = plan.numOfDays
        }

        binding.back.setOnClickListener {
            val bundle = Bundle().apply {
                putParcelable("plan", plan)
            }
            findNavController().navigate(R.id.action_finalizeFragment_to_categoryFragment, bundle)
        }

        binding.submit.setOnClickListener {
            val bundle = Bundle().apply {
                putParcelable("plan", plan)
            }
            findNavController().navigate(R.id.action_finalizeFragment_to_genDayDetailsFragment, bundle)
        }
    }
}