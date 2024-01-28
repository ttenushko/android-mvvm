package com.ttenushko.mvvm.demo.presentation.screen.placedetails

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.ttenushko.mvvm.demo.databinding.FragmentPlaceDetailsBinding
import com.ttenushko.mvvm.demo.presentation.base.fragment.BaseFragment
import com.ttenushko.mvvm.demo.presentation.base.fragment.BaseMvvmFragment
import javax.inject.Inject

class PlaceDetailsFragment :
    BaseMvvmFragment<PlaceDetailsViewModel.State, PlaceDetailsViewModel>() {

    @Inject
    lateinit var vmProvider: (@JvmSuppressWildcards BaseFragment, @JvmSuppressWildcards PlaceDetailsViewModel.State?) -> @JvmSuppressWildcards PlaceDetailsViewModel

    @Inject
    lateinit var viewModelStatePersistence: ViewModelStatePersistence<@JvmSuppressWildcards PlaceDetailsViewModel.State>
    private var viewBinding: FragmentPlaceDetailsBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View =
        FragmentPlaceDetailsBinding.inflate(inflater, container, false).let {
            viewBinding = it
            it.root
        }

    override fun onDestroyView() {
        super.onDestroyView()
        viewBinding = null
    }

    override fun provideViewModel(savedState: PlaceDetailsViewModel.State?): PlaceDetailsViewModel =
        vmProvider(this, savedState)

    override fun provideViewModelStatePersistence(): ViewModelStatePersistence<PlaceDetailsViewModel.State> =
        viewModelStatePersistence

    companion object {
        private const val ARG_PLACE_ID = "placeId"
        private const val DLG_DELETE_CONFIRMATION = "deleteConfirmation"

        fun args(placeId: Long): Bundle =
            Bundle().apply {
                putLong(ARG_PLACE_ID, placeId)
            }
    }
}