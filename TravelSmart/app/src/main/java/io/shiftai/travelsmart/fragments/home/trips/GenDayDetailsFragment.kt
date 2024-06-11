package io.shiftai.travelsmart.fragments.home.trips

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
import io.shiftai.travelsmart.databinding.FragmentGenDayDetailsBinding
import io.shiftai.travelsmart.databinding.FragmentPlanDetailsBinding
import io.shiftai.travelsmart.util.showBottomNavigationView

class GenDayDetailsFragment : Fragment(R.layout.fragment_gen_day_details) {
    private lateinit var binding: FragmentGenDayDetailsBinding
    private val args by navArgs<GenDayDetailsFragmentArgs>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        showBottomNavigationView()
        binding = FragmentGenDayDetailsBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val plan = args.plan

        binding.apply {
            planTitle.text = plan.planTitle
            daysNum.text = plan.numOfDays + "Days"
            planDesc.text = plan.planDesc
            startDate.text = plan.startDate
            endDate.text = plan.endDate
        }

    }

}