package io.shiftai.travelsmart.fragments.home.trips

import android.app.DatePickerDialog
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.EditText
import androidx.navigation.fragment.findNavController
import io.shiftai.travelsmart.R
import io.shiftai.travelsmart.data.CustomPlan
import io.shiftai.travelsmart.databinding.FragmentDatesBinding
import io.shiftai.travelsmart.util.showBottomNavigationView
import java.text.SimpleDateFormat
import java.util.*

class DatesFragment : Fragment() {
    private lateinit var binding: FragmentDatesBinding
    private lateinit var startDateEditText: EditText
    private lateinit var endDateEditText: EditText
    private val calendar = Calendar.getInstance()
    private var customPlan: CustomPlan? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentDatesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        startDateEditText = binding.startDate
        endDateEditText = binding.endDate

        startDateEditText.setOnClickListener {
            showDatePicker(startDateEditText)
        }

        endDateEditText.setOnClickListener {
            showDatePicker(endDateEditText)
        }

        binding.next.setOnClickListener {
            if (startDateEditText.text.isNotEmpty() && endDateEditText.text.isNotEmpty()) {
                customPlan = CustomPlan(
                    id = "1",
                    destination = binding.searchEditText.text.toString(),
                    planTitle = "Personalized Trip",
                    planDesc = "Enjoy your custom planned journey",
                    startDate = startDateEditText.text.toString(),
                    endDate = endDateEditText.text.toString(),
                    numOfDays = formattedNumOfDays()
                )
            }
            val bundle = Bundle().apply {
                putParcelable("plan", customPlan)
            }
            findNavController().navigate(R.id.action_datesFragment_to_categoryFragment, bundle)
        }

        binding.back.setOnClickListener {
            findNavController().navigate(R.id.action_datesFragment_to_homeFragment)
        }
    }

    private fun showDatePicker(editText: EditText) {
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH)

        val datePickerDialog = DatePickerDialog(
            requireContext(),
            { _, selectedYear, selectedMonth, dayOfMonth ->
                val selectedDate = "${selectedMonth + 1}/$dayOfMonth/$selectedYear"
                editText.setText(selectedDate)
            },
            year, month, dayOfMonth
        )
        datePickerDialog.datePicker.minDate = System.currentTimeMillis() - 1000 // Prevent past dates
        datePickerDialog.show()
    }

    override fun onResume() {
        super.onResume()

        showBottomNavigationView()
    }

    fun formattedNumOfDays(): String {
        val sdf = SimpleDateFormat("MM/dd/yyyy", Locale.getDefault())
        try {
            val start = sdf.parse(startDateEditText.text.toString())
            val end = sdf.parse(endDateEditText.text.toString())
            val diff = end.time - start.time
            val days = diff / (1000 * 60 * 60 * 24) // Convert milliseconds to days
            return days.toString()
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return "0"
    }
}