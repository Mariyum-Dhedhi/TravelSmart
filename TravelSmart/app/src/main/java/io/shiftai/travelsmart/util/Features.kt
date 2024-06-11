package io.shiftai.travelsmart.util

import android.graphics.Color
import android.graphics.LinearGradient
import android.graphics.Shader
import android.view.animation.AnimationUtils
import android.widget.TextView
import androidx.fragment.app.Fragment
import io.shiftai.travelsmart.R

fun Fragment.verticalGradientText(text:TextView) {
    val height = text.textSize

    text.paint.shader = LinearGradient(
        0f, 0f, 0f, height,
        intArrayOf(
            Color.WHITE,
            Color.WHITE,
            Color.parseColor("#80FFFFFF"),
            Color.parseColor("#20FFFFFF")
        ),
        null,
        Shader.TileMode.CLAMP
    )
}

fun Fragment.moveUpTextAnimation(text:TextView) {
    text.startAnimation(
        AnimationUtils.loadAnimation(
        context,
        R.anim.move_up
    ));
}
