package io.shiftai.travelsmart.viewModels

import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.runBlocking
import javax.inject.Inject
import io.shiftai.travelsmart.data.User
import io.shiftai.travelsmart.util.*
import io.shiftai.travelsmart.util.Constants.USER_COLLECTION

@HiltViewModel
class SignUpViewModel @Inject constructor(
    private val firebaseAuth: FirebaseAuth,
    private val db: FirebaseFirestore


): ViewModel(){

    private val _signup = MutableStateFlow<Resource<User>>(Resource.Unspecified())
    val signup: Flow<Resource<User>> = _signup


    private val _validation = Channel<SignupFieldsState>()
    val validation = _validation.receiveAsFlow()

    fun createAccountWithEmailAndPassword(user: User,password:String) {
        if (checkValidation(user, password)) {
            runBlocking {
                _signup.emit(Resource.Loading())
            }
            firebaseAuth.createUserWithEmailAndPassword(user.email, password)
                .addOnSuccessListener {
                    it.user?.let {
                        saveUserInfo(it.uid, user)
                        //_signup.value = Resource.Success(it)
                    }

                }.addOnFailureListener {
                    _signup.value = Resource.Error(it.message.toString())
                }
        } else {
            val signupFieldsState = SignupFieldsState(
                validateEmail(user.email), validatePassword(password)
            )
            runBlocking {
                _validation.send(signupFieldsState)
            }
        }
    }

    private fun saveUserInfo(userUid: String, user: User) {
        db.collection(USER_COLLECTION)
            .document(userUid)
            .set(user)
            .addOnSuccessListener {
                _signup.value = Resource.Success(user)
            }.addOnFailureListener {
                _signup.value = Resource.Error(it.message.toString())
            }
    }


    private fun checkValidation(user: User, password: String): Boolean {
        val emailValidation = validateEmail(user.email)
        val passwordValidation = validatePassword(password)

        return emailValidation is SignupValidation.Success &&
                passwordValidation is SignupValidation.Success
    }
}