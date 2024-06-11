package io.shiftai.travelsmart.activities

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import io.shiftai.travelsmart.R

class SplashActivity : BaseActivity() {
    private val handler = Handler()
    private lateinit var runnable: Runnable

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)

        runnable = Runnable {
            Intent(this, SignInSignUpActivity::class.java).apply {
                startActivity(this)
                finish()
            }
        }

        handler.postDelayed(runnable,200)
    }

    override fun onDestroy() {
        super.onDestroy()

        handler.removeCallbacks(runnable)
    }
}