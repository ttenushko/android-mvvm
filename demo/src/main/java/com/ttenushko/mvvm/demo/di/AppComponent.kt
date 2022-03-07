package com.ttenushko.mvvm.demo.di

import android.content.Context
import com.ttenushko.mvvm.demo.App
import com.ttenushko.mvvm.demo.di.data.DataModule
import com.ttenushko.mvvm.demo.presentation.utils.dagger.ApplicationContext
import dagger.BindsInstance
import dagger.Component

@Component(
    modules = [
        AppModule::class,
        DataModule::class,
        PublicDependenciesProviderModule::class
    ]
)
@ApplicationScope
interface AppComponent : PublicDependencies {

    @Component.Builder
    interface Builder {
        @BindsInstance
        @ApplicationContext
        fun applicationContext(applicationContext: Context): Builder
        fun appModule(appModule: AppModule): Builder
        fun dataModule(dataModule: DataModule): Builder
        fun build(): AppComponent
    }

    fun inject(app: App)
}