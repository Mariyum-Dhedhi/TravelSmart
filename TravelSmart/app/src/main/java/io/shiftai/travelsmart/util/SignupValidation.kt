package io.shiftai.travelsmart.util

sealed class SignupValidation(){
    object Success: SignupValidation()
    data class Failed(val message: String): SignupValidation()
}

data class SignupFieldsState(
    val email: SignupValidation,
    val password: SignupValidation
)

