package com.ttenushko.mvvm.demo.di.presentation.screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.ttenushko.mvvm.android.ViewModelHolder
import com.ttenushko.mvvm.android.ViewModelStatePersistence
import com.ttenushko.mvvm.demo.di.Dependency
import com.ttenushko.mvvm.demo.domain.application.usecase.TrackSavedPlacesUseCase
import com.ttenushko.mvvm.demo.presentation.base.fragment.BaseFragment
import com.ttenushko.mvvm.demo.presentation.base.router.Router
import com.ttenushko.mvvm.demo.presentation.screen.MainRouter
import com.ttenushko.mvvm.demo.presentation.screen.places.PlacesFragment
import com.ttenushko.mvvm.demo.presentation.screen.places.PlacesViewModel
import com.ttenushko.mvvm.demo.presentation.screen.places.PlacesViewModelImpl
import com.ttenushko.mvvm.demo.presentation.screen.places.PlacesViewModelStatePersistence
import dagger.Component
import dagger.Provides

interface PlacesFragmentDependencies : Dependency {
    fun trackSavedPlacesUseCase(): TrackSavedPlacesUseCase
    fun router(): Router<MainRouter.Destination>
}

@dagger.Module
internal class PlacesFragmentModule {

    @Provides
    fun viewModelProvider(
        trackSavedPlacesUseCase: TrackSavedPlacesUseCase,
        router: Router<MainRouter.Destination>
    ): (BaseFragment, PlacesViewModel.State?) -> PlacesViewModel = { fragment, savedState ->
        ViewModelProvider(fragment, object : ViewModelProvider.Factory {
            override fun <T : ViewModel?> create(modelClass: Class<T>): T =
                PlacesViewModelHolder(
                    PlacesViewModelImpl(savedState, trackSavedPlacesUseCase, router)
                ) as T
        }).get(PlacesViewModelHolder::class.java).viewModel
    }

    @Provides
    fun viewModelStatePersistence(): ViewModelStatePersistence<PlacesViewModel.State> =
        PlacesViewModelStatePersistence()

    class PlacesViewModelHolder(viewModel: PlacesViewModel) :
        ViewModelHolder<PlacesViewModel.State, PlacesViewModel>(viewModel)
}

@Component(
    dependencies = [PlacesFragmentDependencies::class],
    modules = [PlacesFragmentModule::class]
)
internal interface PlacesFragmentComponent {
    fun inject(fragment: PlacesFragment)
}



