package com.ttenushko.mvvm.demo.presentation.screen.places

import android.os.Bundle
import com.ttenushko.mvvm.android.ViewModelStatePersistence

class PlacesViewModelStatePersistence : ViewModelStatePersistence<PlacesViewModel.State> {

    override fun saveState(state: PlacesViewModel.State?, outState: Bundle) {
        // do nothing
    }

    override fun restoreState(savedState: Bundle?): PlacesViewModel.State? {
        return null
    }
}