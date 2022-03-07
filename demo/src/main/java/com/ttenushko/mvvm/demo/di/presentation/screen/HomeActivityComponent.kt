package com.ttenushko.mvvm.demo.di.presentation.screen

import com.ttenushko.androidmvi.demo.di.ApplicationDependencies
import com.ttenushko.androidmvi.demo.di.dependency.ComponentDependencies
import com.ttenushko.androidmvi.demo.di.dependency.ComponentDependenciesKey
import com.ttenushko.androidmvi.demo.di.domain.UseCaseModule
import com.ttenushko.androidmvi.demo.presentation.di.annotation.ActivityScope
import com.ttenushko.androidmvi.demo.presentation.screens.MainActivity
import com.ttenushko.androidmvi.demo.presentation.screens.addplace.di.AddPlaceFragmentDependencies
import com.ttenushko.androidmvi.demo.presentation.screens.placedetails.di.PlaceDetailsFragmentDependencies
import com.ttenushko.androidmvi.demo.presentation.screens.places.di.PlacesFragmentDependencies
import dagger.Binds
import dagger.Component
import dagger.multibindings.IntoMap

@Component(
    dependencies = [ApplicationDependencies::class],
    modules = [ComponentDependenciesModule::class, UseCaseModule::class]
)
@ActivityScope
interface HomeActivityComponent : PlacesFragmentDependencies, PlaceDetailsFragmentDependencies,
    AddPlaceFragmentDependencies {

    @Component.Builder
    interface Builder {
        fun applicationDependencies(applicationDependencies: ApplicationDependencies): Builder
        fun useCaseModule(useCaseModule: UseCaseModule): Builder
        fun build(): HomeActivityComponent
    }

    fun inject(activity: MainActivity)
}

@dagger.Module
abstract class ComponentDependenciesModule {
    @Binds
    @IntoMap
    @ComponentDependenciesKey(PlacesFragmentDependencies::class)
    abstract fun bindPlacesFragmentDependencies(component: HomeActivityComponent): ComponentDependencies

    @Binds
    @IntoMap
    @ComponentDependenciesKey(PlaceDetailsFragmentDependencies::class)
    abstract fun bindPlaceDetailsFragmentDependencies(component: HomeActivityComponent): ComponentDependencies

    @Binds
    @IntoMap
    @ComponentDependenciesKey(AddPlaceFragmentDependencies::class)
    abstract fun bindAddPlaceDependencies(component: HomeActivityComponent): ComponentDependencies
}