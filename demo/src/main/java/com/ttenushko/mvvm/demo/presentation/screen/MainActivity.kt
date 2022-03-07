package com.ttenushko.mvvm.demo.presentation.screen

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.widget.Toolbar
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.ttenushko.mvvm.demo.R
import com.ttenushko.mvvm.demo.di.domain.UseCaseModule
import com.ttenushko.mvvm.demo.presentation.base.activity.BaseActivity
import com.ttenushko.mvvm.demo.presentation.base.router.RouterProxy
import javax.inject.Inject

class MainActivity : BaseActivity() {

    @Inject
    lateinit var router: MainRouterImpl

    @Inject
    lateinit var routerProxy: RouterProxy<MainRouter.Destination>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        DaggerMainActivityComponent.builder()
            .applicationDependencies(findComponentDependencies())
            .useCaseModule(UseCaseModule())
            .build()
            .inject(this)

        setContentView(R.layout.activity_main)
        router.initialize(this)
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