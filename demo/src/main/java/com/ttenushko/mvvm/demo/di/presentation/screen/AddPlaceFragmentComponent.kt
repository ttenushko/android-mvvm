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
import com.ttenushko.mvvm.demo.presentation.screen.addplace.AddPlaceFragment
import com.ttenushko.mvvm.demo.presentation.screen.addplace.AddPlaceViewModel
import com.ttenushko.mvvm.demo.presentation.screen.addplace.AddPlaceViewModelImpl
import com.ttenushko.mvvm.demo.presentation.screen.addplace.AddPlaceViewModelStatePersistence
import dagger.Component
import dagger.Provides

interface AddPlaceFragmentDependencies : Dependency {
    fun trackSavedPlacesUseCase(): TrackSavedPlacesUseCase
    fun router(): Router<MainRouter.Destination>
}

@dagger.Module
internal class AddPlaceFragmentModule(
    private val search: String
) {
    @Provides
    fun viewModelProvider(
        trackSavedPlacesUseCase: TrackSavedPlacesUseCase,
        router: Router<MainRouter.Destination>
    ): (BaseFragment, AddPlaceViewModel.State?) -> AddPlaceViewModel = { fragment, savedState ->
        ViewModelProvider(fragment, object : ViewModelProvider.Factory {
            override fun <T : ViewModel?> create(modelClass: Class<T>): T =
                AddPlaceViewModelHolder(
                    AddPlaceViewModelImpl(savedState, trackSavedPlacesUseCase, router)
                ) as T
        }).get(AddPlaceViewModelHolder::class.java).viewModel
    }

    @Provides
    fun viewModelStatePersistence(): ViewModelStatePersistence<AddPlaceViewModel.State> =
        AddPlaceViewModelStatePersistence()

    class AddPlaceViewModelHolder(viewModel: AddPlaceViewModel) :
        ViewModelHolder<AddPlaceViewModel.State, AddPlaceViewModel>(viewModel)
}

@Component(
    dependencies = [AddPlaceFragmentDependencies::class],
    modules = [AddPlaceFragmentModule::class]
)
internal interface AddPlaceFragmentComponent {

    @Component.Builder
    interface Builder {
        fun addPlaceFragmentDependencies(addPlaceFragmentDependencies: AddPlaceFragmentDependencies): Builder
        fun addPlaceFragmentModule(addPlaceFragmentModule: AddPlaceFragmentModule): Builder
        fun build(): AddPlaceFragmentComponent
    }

    fun inject(fragment: AddPlaceFragment)
}
