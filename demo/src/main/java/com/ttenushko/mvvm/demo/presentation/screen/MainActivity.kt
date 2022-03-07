package com.ttenushko.mvvm.demo.presentation.screen

import android.os.Bundle
import androidx.appcompat.widget.Toolbar
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.ttenushko.mvvm.demo.R
import com.ttenushko.mvvm.demo.di.DependenciesProvider
import com.ttenushko.mvvm.demo.di.ProvidesDependencies
import com.ttenushko.mvvm.demo.di.domain.UseCaseModule
import com.ttenushko.mvvm.demo.di.presentation.screen.DaggerMainActivityComponent
import com.ttenushko.mvvm.demo.di.presentation.screen.MainActivityModule
import com.ttenushko.mvvm.demo.presentation.base.activity.BaseActivity
import com.ttenushko.mvvm.demo.presentation.base.router.RouterProxy
import com.ttenushko.mvvm.demo.presentation.utils.dagger.findDependency
import javax.inject.Inject

class MainActivity : BaseActivity(), ProvidesDependencies {

    @Inject
    lateinit var router: MainRouterImpl

    @Inject
    lateinit var routerProxy: RouterProxy<MainRouter.Destination>

    @Suppress("ProtectedInFinal")
    @Inject
    override lateinit var dependenciesProvider: DependenciesProvider
        protected set

    override fun onCreate(savedInstanceState: Bundle?) {

        DaggerMainActivityComponent.builder()
            .publicDependencies(findDependency())
            .useCaseModule(UseCaseModule())
            .mainActivityModule(MainActivityModule(this))
            .build()
            .inject(this)

        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)
    }

    override fun onStart() {
        super.onStart()
        routerProxy.attach(router)
    }

    override fun onStop() {
        super.onStop()
        routerProxy.detach(router)
    }

    override fun onNavigationBackStackChanged() {
        super.onNavigationBackStackChanged()
        navigationTopmostFragment?.view?.findViewById<Toolbar>(R.id.toolbar)?.let { toolbar ->
            val navController = findNavController(R.id.nav_host_fragment)
            val appBarConfiguration = AppBarConfiguration(navController.graph)
            setSupportActionBar(toolbar)
            toolbar.setupWithNavController(navController, appBarConfiguration)
            setupActionBarWithNavController(navController)
        }
    }
}