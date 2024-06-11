package io.shiftai.travelsmart.fragments.home.trips

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import io.shiftai.travelsmart.R
import io.shiftai.travelsmart.databinding.FragmentCategoryBinding
import io.shiftai.travelsmart.databinding.FragmentDatesBinding
import io.shiftai.travelsmart.fragments.home.plan.PlanDetailsFragmentArgs

class CategoryFragment : Fragment(R.layout.fragment_category) {
    private lateinit var binding: FragmentCategoryBinding
    private val args by navArgs<CategoryFragmentArgs>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCategoryBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val plan = args.plan

        binding.next.setOnClickListener {
            val bundle = Bundle().apply {
                putParcelable("plan", plan)
            }
            findNavController().navigate(R.id.action_categoryFragment_to_finalizeFragment, bundle)
        }

        binding.back.setOnClickListener {
            val bundle = Bundle().apply {
                putParcelable("plan", plan)
            }
            findNavController().navigate(R.id.action_categoryFragment_to_datesFragment, bundle)
        }
    }
}