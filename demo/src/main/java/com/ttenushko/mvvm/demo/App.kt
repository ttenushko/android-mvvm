package com.ttenushko.mvvm.demo

import android.app.Application
import com.ttenushko.mvvm.demo.di.DaggerApplicationComponent
import com.ttenushko.mvvm.demo.di.DependenciesProvider
import com.ttenushko.mvvm.demo.di.ProvidesDependencies
import com.ttenushko.mvvm.demo.di.AppModule
import com.ttenushko.mvvm.demo.di.data.DataModule
import javax.inject.Inject

class App : Application(), ProvidesDependencies {

    companion object {
        lateinit var instance: App
            private set
    }

    @Suppress("ProtectedInFinal")
    @Inject
    override lateinit var dependenciesProvider: DependenciesProvider
        protected set

    override fun onCreate() {
        instance = this
        super.onCreate()

        DaggerApplicationComponent.builder()
            .applicationContext(this)
            .applicationModule(AppModule(Config.IS_DEBUG))
            .dataModule(DataModule(Config.IS_DEBUG))
            .build()
            .inject(this)
    }
}