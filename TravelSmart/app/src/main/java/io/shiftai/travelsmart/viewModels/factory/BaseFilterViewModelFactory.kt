package io.shiftai.travelsmart.viewModels.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.google.firebase.firestore.FirebaseFirestore
import io.shiftai.travelsmart.data.FilterType
import io.shiftai.travelsmart.viewModels.FilterViewModel

class BaseCategoryViewModelFactory(
    private val fireStore: FirebaseFirestore,
    private val filterType: FilterType
): ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return FilterViewModel(fireStore,filterType) as T
    }
}