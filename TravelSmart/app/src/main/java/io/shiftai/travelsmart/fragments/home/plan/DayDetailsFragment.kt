package io.shiftai.travelsmart.fragments.home.plan

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import io.shiftai.travelsmart.R
import io.shiftai.travelsmart.adaptors.HomeViewPagerAdaptor
import io.shiftai.travelsmart.databinding.FragmentDayDetailsBinding
import io.shiftai.travelsmart.databinding.FragmentPlanDetailsBinding
import io.shiftai.travelsmart.util.showBottomNavigationView

class DayDetailsFragment : Fragment(R.layout.fragment_day_details) {
    private lateinit var binding: FragmentDayDetailsBinding
    private val args by navArgs<DayDetailsFragmentArgs>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        showBottomNavigationView()
        binding = FragmentDayDetailsBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val plan = args.plan
        val day = args.day

        binding.apply {
            budget.text = "Rs. ${plan.formattedBudget()}"
            planTitle.text = plan.planTitle
            Glide.with(this@DayDetailsFragment).load(plan.images[1]).into(imgPlan)
            daysNum.text = "${plan.formattedNumOfDays()} Days"
            reviewTxt.text = "${plan.formattedReview()} out of 5"
            planDesc.text = day.desc
            dayDesc.text = "Day " + day.formattedNum() + " - Trip To " + plan.destination
        }

        binding.back.setOnClickListener {
            val bundle = Bundle().apply {
                putParcelable("plan", plan)
            }
            findNavController().navigate(R.id.action_dayDetailsFragment_to_planDetailsFragment, bundle)
        }

        binding.book.setOnClickListener {
            Toast.makeText(requireContext(), "Booking Details Are Send To The Agent", Toast.LENGTH_SHORT).show()
            findNavController().navigate(R.id.action_dayDetailsFragment_to_homeFragment)
        }

    }

}