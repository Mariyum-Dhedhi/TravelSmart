package io.shiftai.travelsmart.data

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Plan(
    val id: String,
    val destination: String,
    val planTitle: String,
    val planDesc: String,
    val numOfDays: Float,
    val included: String? = null,
    val excluded: String? = null,
    val budget: Float,
    val review: Float,
    val images: List<String>
): Parcelable {
    constructor():this("0","","","",0f,"","",0f,1f,images = emptyList())

    fun formattedNumOfDays(): String {
        return "%.0f".format(numOfDays)
    }

    fun formattedReview(): String {
        return "%.1f".format(review)
    }

    fun formattedBudget(): String {
        return "%.0f".format(budget)
    }
}