package com.ttenushko.mvvm.demo.presentation.screen.places

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.ttenushko.mvvm.android.ViewModelStatePersistence
import com.ttenushko.mvvm.demo.databinding.FragmentPlacesBinding
import com.ttenushko.mvvm.demo.presentation.base.fragment.BaseFragment
import com.ttenushko.mvvm.demo.presentation.base.fragment.BaseMvvmFragment
import javax.inject.Inject

class PlacesFragment(
    private val vmProvider: (BaseFragment, PlacesViewModel.State?) -> PlacesViewModel
) : BaseMvvmFragment<PlacesViewModel.State, PlacesViewModel>() {

    @Inject
    lateinit var viewModelStatePersistence: ViewModelStatePersistence<PlacesViewModel.State>
    private var viewBinding: FragmentPlacesBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View =
        FragmentPlacesBinding.inflate(inflater, container, false).let {
            viewBinding = it
            it.root
        }

    override fun onDestroyView() {
        super.onDestroyView()
        viewBinding = null
    }

    override fun provideViewModel(savedState: PlacesViewModel.State?): PlacesViewModel =
        vmProvider(this, savedState)

    override fun provideViewModelStatePersistence(): ViewModelStatePersistence<PlacesViewModel.State> =
        viewModelStatePersistence
}