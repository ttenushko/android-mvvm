package com.ttenushko.mvvm.demo.di

import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class PublicDependenciesProviderModule {
    @Binds
    @IntoMap
    @DependencyKey(PublicDependencies::class)
    abstract fun publicDependencies(component: AppComponent): Dependency
}