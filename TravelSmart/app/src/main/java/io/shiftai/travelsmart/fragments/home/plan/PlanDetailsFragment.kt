package io.shiftai.travelsmart.fragments.home.plan

import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.PointF
import android.graphics.pdf.PdfDocument
import android.os.Bundle
import android.os.Environment
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
import io.shiftai.travelsmart.databinding.FragmentPlanDetailsBinding
import io.shiftai.travelsmart.util.showBottomNavigationView
import java.io.File
import java.io.FileOutputStream
import java.io.IOException

class PlanDetailsFragment : Fragment(R.layout.fragment_plan_details) {
    private lateinit var binding: FragmentPlanDetailsBinding
    private val args by navArgs<PlanDetailsFragmentArgs>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        showBottomNavigationView()
        binding = FragmentPlanDetailsBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val plan = args.plan

        binding.apply {
            budget.text = "Rs. ${plan.formattedBudget()}"
            planTitle.text = plan.planTitle
            Glide.with(this@PlanDetailsFragment).load(plan.images[1]).into(imgPlan)
            daysNum.text = "${plan.formattedNumOfDays()} Days"
            reviewTxt.text = "${plan.formattedReview()} out of 5"
            planDesc.text = plan.planDesc
            included.text = plan.included
            excluded.text = plan.excluded
        }

        binding.back.setOnClickListener {
            val bundle = Bundle().apply {
                putParcelable("plan", plan)
            }
            findNavController().navigate(R.id.action_planDetailsFragment_to_planFragment, bundle)
        }

        binding.download.setOnClickListener {
            generateAndPrintPDF()
        }

        binding.book.setOnClickListener {
            Toast.makeText(requireContext(), "Booking Details Are Send To The Agent", Toast.LENGTH_SHORT).show()
            findNavController().navigate(R.id.action_planDetailsFragment_to_homeFragment)
        }

        val fragments = listOf(
            PlanPagerFragment.newInstance(plan)
        )

        val adapter = HomeViewPagerAdaptor(fragments, childFragmentManager, lifecycle)
        binding.viewpagerDays.adapter = adapter
    }

    fun generateAndPrintPDF() {
        val document = PdfDocument()

        val viewHeight = binding.root.height
        val pageSize = PointF(binding.root.width.toFloat(), viewHeight.toFloat())

        val pageCount = (viewHeight / pageSize.y).toInt() + 1
        var pageIndex = 0
        var yPosition = 0f

        while (pageIndex < pageCount) {
            val pageInfo = PdfDocument.PageInfo.Builder(pageSize.x.toInt(), pageSize.y.toInt(), pageIndex).create()
            val page = document.startPage(pageInfo)
            val canvas: Canvas = page.canvas
            val paint = Paint()

            canvas.translate(0f, -yPosition)

            binding.root.draw(canvas)

            document.finishPage(page)

            yPosition += pageSize.y
            pageIndex++
        }

        val directoryPath = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)
        val filePath = File(directoryPath, "plan_details.pdf")

        try {
            val outputStream = FileOutputStream(filePath)
            document.writeTo(outputStream)
            Toast.makeText(requireContext(), "PDF Generated and Saved", Toast.LENGTH_SHORT).show()
        } catch (e: IOException) {
            e.printStackTrace()
            Toast.makeText(requireContext(), "Error generating PDF", Toast.LENGTH_SHORT).show()
        }

        document.close()
    }
}