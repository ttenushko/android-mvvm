package com.ttenushko.mvvm.demo.presentation.screen.addplace

import com.ttenushko.mvvm.demo.domain.application.usecase.TrackSavedPlacesUseCase
import com.ttenushko.mvvm.demo.presentation.base.router.Router
import com.ttenushko.mvvm.demo.presentation.base.viewmodel.BaseViewModel
import com.ttenushko.mvvm.demo.presentation.screen.MainRouter

class AddPlaceViewModelImpl(
    savedState: AddPlaceViewModel.State?,
    private val trackSavedPlacesUseCase: TrackSavedPlacesUseCase,
    private val router: Router<MainRouter.Destination>
) : BaseViewModel<AddPlaceViewModel.State>(), AddPlaceViewModel {

    override fun saveState(): AddPlaceViewModel.State? =
        null
}