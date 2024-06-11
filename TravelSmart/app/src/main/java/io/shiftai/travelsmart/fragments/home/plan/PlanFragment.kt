package io.shiftai.travelsmart.fragments.home.plan

import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.PointF
import android.graphics.drawable.Drawable
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
import com.bumptech.glide.request.target.CustomTarget
import io.shiftai.travelsmart.R
import io.shiftai.travelsmart.data.Plan
import io.shiftai.travelsmart.databinding.FragmentPlanBinding
import io.shiftai.travelsmart.util.hideBottomNavigationView
import java.io.File
import java.io.FileOutputStream
import java.io.IOException

class PlanFragment : Fragment(R.layout.fragment_plan) {
    private lateinit var binding: FragmentPlanBinding
    private val args by navArgs<PlanFragmentArgs>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        hideBottomNavigationView()
        binding = FragmentPlanBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val plan = args.plan

        binding.apply {
            destination.text = plan.destination
            planTitle.text = plan.planTitle
            Glide.with(this@PlanFragment).load(plan.images[1]).into(imgPlan)
            description.text = plan.planDesc
        }

        binding.download.setOnClickListener {
            generateAndDownloadPDF(plan)
        }

        binding.viewTrip.setOnClickListener {
            val action = PlanFragmentDirections.actionPlanFragmentToPlanDetailsFragment(plan)
            findNavController().navigate(action)
        }

        binding.weather.setOnClickListener {
            val action = PlanFragmentDirections.actionPlanFragmentToWeatherFragment(plan)
            findNavController().navigate(action)
        }


        binding.back.setOnClickListener {
            findNavController().navigate(R.id.action_planFragment_to_homeFragment)
        }
    }

    private fun generateAndDownloadPDF(plan: Plan) {
        val document = PdfDocument()
        val pageInfo = PdfDocument.PageInfo.Builder(binding.root.width, binding.root.height, 1).create()
        val page = document.startPage(pageInfo)
        val canvas: Canvas = page.canvas
        val paint = Paint()
        paint.textSize = 20f // Increased text size for better visibility

        val context = requireContext()

        // Center align pageTitle
        val pageTitle = "Plan Details"
        val pageTitleWidth = paint.measureText(pageTitle)
        val pageTitleX = (binding.root.width - pageTitleWidth) / 2
        val pageTitleY = 100f // Adjust Y position as needed
        canvas.drawText(pageTitle, pageTitleX, pageTitleY, paint)

        // Draw the specific views and text you want to include in the PDF
        val budgetText = "Budget: Rs. ${plan.formattedBudget()}"
        val planTitleText = "Plan Title: ${plan.planTitle}"
        val daysNumText = "Duration: ${plan.formattedNumOfDays()} Days"
        val reviewText = "Review: ${plan.formattedReview()} out of 5"
        val planDescText = "Plan Description: ${plan.planDesc}"
        val includedText = "Included: ${plan.included}"
        val excludedText = "Excluded: ${plan.excluded}"

        val textMargin = 20f
        var textYPosition = pageTitleY + paint.fontSpacing * 2 // Start after pageTitle

        canvas.drawText(budgetText, textMargin, textYPosition, paint)
        textYPosition += paint.fontSpacing

        canvas.drawText(planTitleText, textMargin, textYPosition, paint)
        textYPosition += paint.fontSpacing

        canvas.drawText(daysNumText, textMargin, textYPosition, paint)
        textYPosition += paint.fontSpacing

        canvas.drawText(reviewText, textMargin, textYPosition, paint)
        textYPosition += paint.fontSpacing

        textYPosition = drawMultiLineText(planDescText, canvas, textMargin, textYPosition, paint)
        textYPosition = drawMultiLineText(includedText, canvas, textMargin, textYPosition, paint)
        textYPosition = drawMultiLineText(excludedText, canvas, textMargin, textYPosition, paint)

        document.finishPage(page)

        // Save the PDF to external storage
        val directoryPath = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)
        val filePath = File(directoryPath, "plan.pdf")

        try {
            val outputStream = FileOutputStream(filePath)
            document.writeTo(outputStream)
            Toast.makeText(context, "PDF Generated and Saved", Toast.LENGTH_SHORT).show()
        } catch (e: IOException) {
            e.printStackTrace()
            Toast.makeText(context, "Error generating PDF", Toast.LENGTH_SHORT).show()
        }

        document.close()
    }

    private fun drawMultiLineText(text: String, canvas: Canvas, x: Float, y: Float, paint: Paint): Float {
        val words = text.split(" ")
        var currentY = y
        var line = ""

        for (word in words) {
            val testLine = if (line.isEmpty()) word else "$line $word"
            val testWidth = paint.measureText(testLine)
            if (testWidth > canvas.width - 40) {
                canvas.drawText(line, x, currentY, paint)
                line = word
                currentY += paint.fontSpacing
            } else {
                line = testLine
            }
        }

        if (line.isNotEmpty()) {
            canvas.drawText(line, x, currentY, paint)
            currentY += paint.fontSpacing
        }

        return currentY
    }

}