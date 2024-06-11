package io.shiftai.travelsmart.util

import android.view.View
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import io.shiftai.travelsmart.activities.HomeActivity

fun Fragment.hideBottomNavigationView(){
    val bottomNavigationView =
        (activity as HomeActivity).findViewById<BottomNavigationView>(
            io.shiftai.travelsmart.R.id.bottomNavigation
        )
    val buttonView =
        (activity as HomeActivity).findViewById<FloatingActionButton>(
            io.shiftai.travelsmart.R.id.AIButton
        )

    val view =
        (activity as HomeActivity).findViewById<View>(
            io.shiftai.travelsmart.R.id.holder
        )

    view.visibility                 = android.view.View.GONE
    buttonView.visibility           = android.view.View.GONE
    bottomNavigationView.visibility = android.view.View.GONE
}

fun Fragment.showBottomNavigationView(){
    val bottomNavigationView =
        (activity as HomeActivity).findViewById<BottomNavigationView>(
            io.shiftai.travelsmart.R.id.bottomNavigation
        )

    val buttonView =
        (activity as HomeActivity).findViewById<FloatingActionButton>(
            io.shiftai.travelsmart.R.id.AIButton
        )

    val view =
        (activity as HomeActivity).findViewById<View>(
            io.shiftai.travelsmart.R.id.holder
        )

    view.visibility                 = android.view.View.VISIBLE
    buttonView.visibility           = android.view.View.VISIBLE
    bottomNavigationView.visibility = android.view.View.VISIBLE
}