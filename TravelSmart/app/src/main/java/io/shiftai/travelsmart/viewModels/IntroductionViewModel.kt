package io.shiftai.travelsmart.viewModels

import android.content.SharedPreferences
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.lifecycle.HiltViewModel
import io.shiftai.travelsmart.R
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import io.shiftai.travelsmart.util.Constants.INTRODUCTION_KEY
import javax.inject.Inject

@HiltViewModel
class IntroductionViewModel @Inject constructor(
    private val sharedPreferences: SharedPreferences,
    private val firebaseAuth: FirebaseAuth
) : ViewModel() {

    private val _navigate = MutableStateFlow(0)
    val navigate: StateFlow<Int> = _navigate

    companion object{
        const val HOME_ACTIVITY = 20
        const val SIGN_IN_FRAGMENT = R.id.action_introductionFragment_to_signinFragment
    }

    init {
        val isButtonClicked = sharedPreferences.getBoolean(INTRODUCTION_KEY,false)


        val user = firebaseAuth.currentUser

        if (user != null){
            viewModelScope.launch {
                _navigate.emit(HOME_ACTIVITY)
            }
        }else if (isButtonClicked){
            viewModelScope.launch {
                _navigate.emit(SIGN_IN_FRAGMENT)
            }
        }else{
        }
    }

    fun startButtonClick(){
        sharedPreferences.edit().putBoolean(INTRODUCTION_KEY,true).apply()
    }
}