package com.ttenushko.mvvm.demo.presentation.base.router

interface Router<D : Router.Destination> {
    fun navigateTo(destination: D)

    interface Destination
}