package io.shiftai.travelsmart.data

sealed class FilterType(val category: String) {

    object All: FilterType("All")
    object Latest: FilterType("Latest")
    object Popular: FilterType("Popular")

}