package com.ttenushko.mvvm.demo.di.presentation.screen

import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.NavHostFragment
import com.ttenushko.mvvm.demo.R
import com.ttenushko.mvvm.demo.di.Dependency
import com.ttenushko.mvvm.demo.di.DependencyKey
import com.ttenushko.mvvm.demo.di.PublicDependencies
import com.ttenushko.mvvm.demo.di.domain.UseCaseModule
import com.ttenushko.mvvm.demo.presentation.base.activity.BaseActivity
import com.ttenushko.mvvm.demo.presentation.base.router.Router
import com.ttenushko.mvvm.demo.presentation.base.router.RouterProxy
import com.ttenushko.mvvm.demo.presentation.screen.MainActivity
import com.ttenushko.mvvm.demo.presentation.screen.MainRouter
import com.ttenushko.mvvm.demo.presentation.screen.MainRouterImpl
import com.ttenushko.mvvm.demo.presentation.utils.dagger.ActivityScope
import com.ttenushko.mvvm.demo.presentation.utils.task.TaskExecutorFactory
import dagger.Binds
import dagger.Component
import dagger.Provides
import dagger.multibindings.IntoMap

@Component(
    dependencies = [PublicDependencies::class],
    modules = [ComponentDependenciesModule::class, UseCaseModule::class, MainActivityModule::class]
)
@ActivityScope
interface MainActivityComponent : PlacesFragmentDependencies, PlaceDetailsFragmentDependencies,
    AddPlaceFragmentDependencies {

    @Component.Builder
    interface Builder {
        fun publicDependencies(publicDependencies: PublicDependencies): Builder
        fun useCaseModule(useCaseModule: UseCaseModule): Builder
        fun mainActivityModule(mainActivityModule: MainActivityModule): Builder
        fun build(): MainActivityComponent
    }

    fun inject(activity: MainActivity)
}

@dagger.Module
class MainActivityModule(
    private val activity: BaseActivity
) {
    @Provides
    fun mainRouter(router: RouterProxy<MainRouter.Destination>): Router<MainRouter.Destination> =
        router

    @Provides
    fun mainRouterImpl(): MainRouterImpl =
        MainRouterImpl {
            activity.supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        }

    @ActivityScope
    @Provides
    fun taskExecutorFactory(): TaskExecutorFactory =
        TaskExecutorFactory.create(activity.lifecycleScope)
}

@dagger.Module
abstract class ComponentDependenciesModule {
    @Binds
    @IntoMap
    @DependencyKey(PlacesFragmentDependencies::class)
    abstract fun bindPlacesFragmentDependencies(component: MainActivityComponent): Dependency

    @Binds
    @IntoMap
    @DependencyKey(PlaceDetailsFragmentDependencies::class)
    abstract fun bindPlaceDetailsFragmentDependencies(component: MainActivityComponent): Dependency

    @Binds
    @IntoMap
    @DependencyKey(AddPlaceFragmentDependencies::class)
    abstract fun bindAddPlaceDependencies(component: MainActivityComponent): Dependency
}