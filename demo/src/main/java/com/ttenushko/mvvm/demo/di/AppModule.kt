package com.ttenushko.mvvm.demo.di

import android.app.Application
import android.content.Context
import com.squareup.picasso.Picasso
import com.ttenushko.mvvm.demo.presentation.base.router.RouterProxy
import com.ttenushko.mvvm.demo.presentation.screen.MainRouter
import com.ttenushko.mvvm.demo.presentation.utils.dagger.ApplicationContext
import com.ttenushko.mvvm.demo.presentation.utils.dagger.ApplicationScope
import dagger.Module
import dagger.Provides

@Module
class AppModule(
    private val application: Application,
    private val isDebug: Boolean
) {
    @ApplicationContext
    @ApplicationScope
    @Provides
    fun context(): Context =
        application

    @ApplicationScope
    @Provides
    fun mainRouterProxy(): RouterProxy<MainRouter.Destination> =
        RouterProxy()

    @Provides
    @ApplicationScope
    fun providePicasso(context: Context): Picasso {
        val builder = Picasso.Builder(context.applicationContext)
        if (isDebug) {
            builder.loggingEnabled(true)
            //builder.indicatorsEnabled(true)
        }
        return builder.build()
    }
}