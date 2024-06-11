package io.shiftai.travelsmart.data

data class User(
    val userName: String,
    val password: String,
    val email: String,
    val contact: String,
    var imagePath: String = ""
){
    constructor(): this("","","","","")
}
