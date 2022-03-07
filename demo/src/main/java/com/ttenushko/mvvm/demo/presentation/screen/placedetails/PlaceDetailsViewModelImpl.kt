package com.ttenushko.mvvm.demo.presentation.screen.placedetails

import com.ttenushko.mvvm.demo.domain.application.usecase.TrackSavedPlacesUseCase
import com.ttenushko.mvvm.demo.presentation.base.router.Router
import com.ttenushko.mvvm.demo.presentation.base.viewmodel.BaseViewModel
import com.ttenushko.mvvm.demo.presentation.screen.MainRouter

class PlaceDetailsViewModelImpl(
    savedState: PlaceDetailsViewModel.State?,
    private val trackSavedPlacesUseCase: TrackSavedPlacesUseCase,
    private val router: Router<MainRouter.Destination>
) : BaseViewModel<PlaceDetailsViewModel.State>(), PlaceDetailsViewModel {

    override fun saveState(): PlaceDetailsViewModel.State? =
        null
}