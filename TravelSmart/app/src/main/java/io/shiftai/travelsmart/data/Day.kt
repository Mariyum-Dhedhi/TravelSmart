package io.shiftai.travelsmart.data

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Day(
    val desc: String,
    val num: Float,
    //val placesToVisit: List<Float>
): Parcelable {
    constructor():this("",0f)

    fun formattedNum(): String {
        return "%.0f".format(num)
    }
}