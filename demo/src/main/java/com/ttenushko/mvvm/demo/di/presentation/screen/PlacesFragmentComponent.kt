package com.ttenushko.mvvm.demo.di.presentation.screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.ttenushko.mvvm.android.ViewModelHolder
import com.ttenushko.mvvm.demo.di.DependenciesProvider
import com.ttenushko.mvvm.demo.domain.application.usecase.TrackSavedPlacesUseCase
import com.ttenushko.mvvm.demo.presentation.base.router.Router
import com.ttenushko.mvvm.demo.presentation.screen.MainRouter
import com.ttenushko.mvvm.demo.presentation.screen.places.PlacesFragment
import com.ttenushko.mvvm.demo.presentation.screen.places.PlacesViewModel
import com.ttenushko.mvvm.demo.presentation.screen.places.PlacesViewModelImpl
import dagger.Component
import dagger.Provides

interface PlacesFragmentDependencies : DependenciesProvider {
    fun trackSavedPlacesUseCase(): TrackSavedPlacesUseCase
    fun router(): Router<MainRouter.Destination>
}

@dagger.Module
internal class PlacesFragmentModule {

    @Provides
    fun fragment(
        trackSavedPlacesUseCase: TrackSavedPlacesUseCase,
        router: Router<MainRouter.Destination>
    ): PlacesFragment =
        PlacesFragment(
            vmProvider = { fragment, savedState ->
                ViewModelProvider(fragment, object : ViewModelProvider.Factory {
                    override fun <T : ViewModel?> create(modelClass: Class<T>): T =
                        PlacesViewModelHolder(
                            PlacesViewModelImpl(savedState, trackSavedPlacesUseCase, router)
                        ) as T
                }).get(PlacesViewModelHolder::class.java).viewModel
            }
        )
}

@Component(
    dependencies = [PlacesFragmentDependencies::class],
    modules = [PlacesFragmentModule::class, ViewModelBindingModule::class]
)
internal interface PlacesFragmentComponent {
    fun inject(fragment: PlacesFragment)
}

class PlacesViewModelHolder(viewModel: PlacesViewModel) :
    ViewModelHolder<PlacesViewModel.State, PlacesViewModel>(viewModel)


