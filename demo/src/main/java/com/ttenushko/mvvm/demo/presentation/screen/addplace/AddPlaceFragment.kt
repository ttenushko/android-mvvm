package com.ttenushko.mvvm.demo.presentation.screen.addplace

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.ttenushko.mvvm.android.ViewModelStatePersistence
import com.ttenushko.mvvm.demo.databinding.FragmentAddPlaceBinding
import com.ttenushko.mvvm.demo.presentation.base.fragment.BaseFragment
import com.ttenushko.mvvm.demo.presentation.base.fragment.BaseMvvmFragment
import javax.inject.Inject

class AddPlaceFragment : BaseMvvmFragment<AddPlaceViewModel.State, AddPlaceViewModel>() {

    @Inject
    lateinit var vmProvider: (@JvmSuppressWildcards BaseFragment, @JvmSuppressWildcards AddPlaceViewModel.State?) -> @JvmSuppressWildcards AddPlaceViewModel

    @Inject
    lateinit var viewModelStatePersistence: ViewModelStatePersistence<@JvmSuppressWildcards AddPlaceViewModel.State>
    private var viewBinding: FragmentAddPlaceBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View =
        FragmentAddPlaceBinding.inflate(inflater, container, false).let {
            viewBinding = it
            it.root
        }

    override fun onDestroyView() {
        super.onDestroyView()
        viewBinding = null
    }

    override fun provideViewModel(savedState: AddPlaceViewModel.State?): AddPlaceViewModel =
        vmProvider(this, savedState)

    override fun provideViewModelStatePersistence(): ViewModelStatePersistence<AddPlaceViewModel.State> =
        viewModelStatePersistence

    companion object {
        private const val ARG_SEARCH = "search"
        fun args(search: String): Bundle =
            Bundle().apply {
                putString(ARG_SEARCH, search)
            }
    }
}