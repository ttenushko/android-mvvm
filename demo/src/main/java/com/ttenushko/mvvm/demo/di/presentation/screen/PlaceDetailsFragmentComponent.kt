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
import com.ttenushko.mvvm.demo.presentation.screen.placedetails.PlaceDetailsFragment
import com.ttenushko.mvvm.demo.presentation.screen.placedetails.PlaceDetailsViewModel
import com.ttenushko.mvvm.demo.presentation.screen.placedetails.PlaceDetailsViewModelImpl
import com.ttenushko.mvvm.demo.presentation.screen.placedetails.PlaceDetailsViewModelStatePersistence
import dagger.Component
import dagger.Provides

interface PlaceDetailsFragmentDependencies : Dependency {
    fun trackSavedPlacesUseCase(): TrackSavedPlacesUseCase
    fun router(): Router<MainRouter.Destination>
}

@dagger.Module
internal class PlaceDetailsFragmentModule(
    private val placeId: Long
) {
    @Provides
    fun viewModelProvider(
        trackSavedPlacesUseCase: TrackSavedPlacesUseCase,
        router: Router<MainRouter.Destination>
    ): (BaseFragment, PlaceDetailsViewModel.State?) -> PlaceDetailsViewModel =
        { fragment, savedState ->
            ViewModelProvider(fragment, object : ViewModelProvider.Factory {
                override fun <T : ViewModel?> create(modelClass: Class<T>): T =
                    PlaceDetailsViewModelHolder(
                        PlaceDetailsViewModelImpl(savedState, trackSavedPlacesUseCase, router)
                    ) as T
            }).get(PlaceDetailsViewModelHolder::class.java).viewModel
        }

    @Provides
    fun viewModelStatePersistence(): ViewModelStatePersistence<PlaceDetailsViewModel.State> =
        PlaceDetailsViewModelStatePersistence()

    class PlaceDetailsViewModelHolder(viewModel: PlaceDetailsViewModel) :
        ViewModelHolder<PlaceDetailsViewModel.State, PlaceDetailsViewModel>(viewModel)
}

@Component(
    dependencies = [PlaceDetailsFragmentDependencies::class],
    modules = [PlaceDetailsFragmentModule::class]
)
internal interface PlaceDetailsFragmentComponent {

    @Component.Builder
    interface Builder {
        fun placeDetailsFragmentDependencies(placeDetailsFragmentDependencies: PlaceDetailsFragmentDependencies): Builder
        fun placeDetailsFragmentModule(placeDetailsFragmentModule: PlaceDetailsFragmentModule): Builder
        fun build(): PlaceDetailsFragmentComponent
    }

    fun inject(fragment: PlaceDetailsFragment)
}
