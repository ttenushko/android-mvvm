package com.ttenushko.mvvm.demo.presentation.screen

import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.ttenushko.mvvm.demo.R
import com.ttenushko.mvvm.demo.presentation.base.router.Router
import com.ttenushko.mvvm.demo.presentation.screen.addplace.AddPlaceFragment
import com.ttenushko.mvvm.demo.presentation.screen.placedetails.PlaceDetailsFragment
import com.ttenushko.mvvm.demo.presentation.utils.getActionIdByDestinationId

class MainRouterImpl(
    private val navHostFragmentProvider: () -> NavHostFragment,
) : Router<MainRouter.Destination> {

    private val navController: NavController by lazy { navHostFragmentProvider().navController }

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