package io.shiftai.travelsmart.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import dagger.Provides
import dagger.hilt.android.lifecycle.HiltViewModel
import io.shiftai.travelsmart.data.FilterType
import io.shiftai.travelsmart.data.Plan
import io.shiftai.travelsmart.util.Resource
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Singleton

@HiltViewModel
class FilterViewModel @Inject constructor(
    private val fireStore: FirebaseFirestore,
    private val filterType: FilterType,
) : ViewModel() {


    private val _plans = MutableStateFlow<Resource<List<Plan>>>(Resource.Unspecified())
    val plans = _plans.asStateFlow()

    init {
        fetchPlans()
    }

    fun fetchPlans() {
        viewModelScope.launch {
            _plans.emit(Resource.Loading())
        }
        when (filterType) {
            FilterType.All -> {
                fireStore.collection("agentPlans")
                    .get()
                    .addOnSuccessListener {
                        val plans = it.toObjects(Plan::class.java)
                        viewModelScope.launch {
                            _plans.emit(Resource.Success(plans))
                        }
                    }.addOnFailureListener {
                        viewModelScope.launch {
                            _plans.emit(Resource.Error(it.message.toString()))
                        }
                    }
            }
            FilterType.Latest -> {
                fireStore.collection("agentPlans")
                    .orderBy("dateAdded", Query.Direction.DESCENDING)
                    .get()
                    .addOnSuccessListener {
                        val plans = it.toObjects(Plan::class.java)
                        viewModelScope.launch {
                            _plans.emit(Resource.Success(plans))
                        }
                    }.addOnFailureListener {
                        viewModelScope.launch {
                            _plans.emit(Resource.Error(it.message.toString()))
                        }
                    }
            }
            FilterType.Popular -> {
                fireStore.collection("agentPlans")
                    .orderBy("review", Query.Direction.DESCENDING)
                    .get()
                    .addOnSuccessListener {
                        val plans = it.toObjects(Plan::class.java)
                        viewModelScope.launch {
                            _plans.emit(Resource.Success(plans))
                        }
                    }.addOnFailureListener {
                        viewModelScope.launch {
                            _plans.emit(Resource.Error(it.message.toString()))
                        }
                    }
            }
        }
    }
}