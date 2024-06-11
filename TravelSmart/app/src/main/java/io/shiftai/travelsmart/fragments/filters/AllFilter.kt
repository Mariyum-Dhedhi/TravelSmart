package io.shiftai.travelsmart.fragments.filters

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.firestore.FirebaseFirestore
import dagger.hilt.android.AndroidEntryPoint
import io.shiftai.travelsmart.data.FilterType
import io.shiftai.travelsmart.util.Resource
import io.shiftai.travelsmart.viewModels.FilterViewModel
import io.shiftai.travelsmart.viewModels.factory.BaseCategoryViewModelFactory
import kotlinx.coroutines.flow.collectLatest
import javax.inject.Inject

@AndroidEntryPoint
class AllFilter : BaseFilter() {
    @Inject
    lateinit var fireStore: FirebaseFirestore

    val viewModel by viewModels<FilterViewModel> {
        BaseCategoryViewModelFactory(fireStore, FilterType.All)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        lifecycleScope.launchWhenStarted {
            viewModel.plans.collectLatest {
                when (it) {
                    is Resource.Loading -> {
                        showLoading()
                    }
                    is Resource.Success -> {
                        plansAdapter.differ.submitList(it.data)
                        hideLoading()


                    }
                    is Resource.Error -> {
                        Snackbar.make(requireView(), it.message.toString(), Snackbar.LENGTH_LONG)
                            .show()
                        hideLoading()
                    }
                    else -> Unit
                }
            }
        }
    }
}