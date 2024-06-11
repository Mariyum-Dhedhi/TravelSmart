package io.shiftai.travelsmart.activities

import android.os.Bundle
import dagger.hilt.android.AndroidEntryPoint
import io.shiftai.travelsmart.R

@AndroidEntryPoint
class SignInSignUpActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login_signup)
    }
}