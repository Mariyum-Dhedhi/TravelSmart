package io.shiftai.travelsmart.util

import android.util.Patterns

fun validateEmail(email: String): SignupValidation{
    if (email.isEmpty())
        return SignupValidation.Failed("Please enter user name!")

    if (!Patterns.EMAIL_ADDRESS.matcher(email).matches())
        return SignupValidation.Failed("Please enter a valid email!")

    return SignupValidation.Success
}

fun validatePassword(password: String): SignupValidation {
    if (password.isEmpty())
        return SignupValidation.Failed("Please enter password!")

    if (!isValidPassword(password))
        return SignupValidation.Failed(
            "Password must contain\n" +
                    "   - At least one lowercase letter\n" +
                    "   - One uppercase letter\n" +
                    "   - One digit, and be at least 5 characters long!"
        )

    return SignupValidation.Success
}

private fun isValidPassword(password: String): Boolean {
    val passwordPattern = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d).{5,}$"
    return password.matches(passwordPattern.toRegex())
}