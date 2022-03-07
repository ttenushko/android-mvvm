package com.ttenushko.mvvm.demo.presentation.screen

import androidx.navigation.NavController
import com.ttenushko.mvvm.demo.R
import com.ttenushko.mvvm.demo.presentation.base.router.Router
import com.ttenushko.mvvm.demo.presentation.utils.getActionIdByDestinationId

class MainRouterImpl(
    private val navController: NavController
) : Router<MainRouter.Destination> {

    override fun navigateTo(destination: MainRouter.Destination) {
        val currentDestination =
            navController.currentDestination?.id?.let { navController.graph.findNode(it) }
        when (destination) {
            is MainRouter.Destination.GoBack -> {
                navController.popBackStack()
            }
            is MainRouter.Destination.AddPlace -> {
                val destinationId = R.id.addPlaceFragment
                val actionId =
                    currentDestination?.getActionIdByDestinationId(destinationId) ?: destinationId
                navController.navigate(
                    actionId,
                    AddPlaceFragment.args(destination.search)
                )
            }
            is MainRouter.Destination.PlaceDetails -> {
                val destinationId = R.id.placeDetailsFragment
                val actionId =
                    currentDestination?.getActionIdByDestinationId(destinationId) ?: destinationId
                navController.navigate(
                    actionId,
                    PlaceDetailsFragment.args(destination.placeId)
                )
            }
        }
    }
}