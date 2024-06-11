package io.shiftai.travelsmart.viewModels

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import dagger.Provides
import dagger.hilt.android.lifecycle.HiltViewModel
import io.shiftai.travelsmart.data.Day
import io.shiftai.travelsmart.data.FilterType
import io.shiftai.travelsmart.data.Plan
import io.shiftai.travelsmart.util.Resource
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Singleton

@HiltViewModel
class DayViewModel @Inject constructor(
    private val fireStore: FirebaseFirestore,
) : ViewModel() {

    private val _days = MutableStateFlow<Resource<List<Day>>>(Resource.Unspecified())
    val day = _days.asStateFlow()

    init {
        fetchPlans()
    }

    private fun fetchPlans() {
        viewModelScope.launch {
            _days.emit(Resource.Loading())
        }
        fireStore.collection("agentPlans")
            .document("wmTKEWcj1rIsnzc6gElw")
            .collection("daysList")
            .orderBy("num", Query.Direction.ASCENDING)
            .get()
            .addOnSuccessListener {
                val day = it.toObjects(Day::class.java)
                viewModelScope.launch {
                    _days.emit(Resource.Success(day))
                }
            }.addOnFailureListener {
                viewModelScope.launch {
                    _days.emit(Resource.Error(it.message.toString()))
                }
            }
    }
}
