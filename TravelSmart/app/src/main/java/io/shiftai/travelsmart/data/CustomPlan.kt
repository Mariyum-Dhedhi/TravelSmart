package io.shiftai.travelsmart.data

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import java.text.SimpleDateFormat
import java.util.*

@Parcelize
data class CustomPlan(
    val id: String,
    val destination: String,
    val planTitle: String,
    val planDesc: String,
    val numOfDays: String,
    val startDate: String,
    val endDate: String
): Parcelable {
    constructor():this("0","","","","","","")
}