package com.ttenushko.mvvm.demo.presentation.screen.places

import com.ttenushko.mvvm.demo.domain.application.usecase.TrackSavedPlacesUseCase
import com.ttenushko.mvvm.demo.presentation.base.router.Router
import com.ttenushko.mvvm.demo.presentation.base.viewmodel.BaseViewModel
import com.ttenushko.mvvm.demo.presentation.screen.MainRouter

class PlacesViewModelImpl(
    savedState:PlacesViewModel.State?,
    private val trackSavedPlacesUseCase: TrackSavedPlacesUseCase,
    private val router: Router<MainRouter.Destination>
) : BaseViewModel<PlacesViewModel.State>(), PlacesViewModel {

    override fun saveState(): PlacesViewModel.State? =
        null
}