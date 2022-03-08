package com.ttenushko.mvvm.demo.presentation.screen.places

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.ttenushko.mvvm.android.ViewModelStatePersistence
import com.ttenushko.mvvm.demo.databinding.FragmentPlacesBinding
import com.ttenushko.mvvm.demo.di.presentation.screen.DaggerPlacesFragmentComponent
import com.ttenushko.mvvm.demo.di.presentation.screen.PlacesFragmentModule
import com.ttenushko.mvvm.demo.domain.weather.model.Place
import com.ttenushko.mvvm.demo.presentation.base.fragment.BaseFragment
import com.ttenushko.mvvm.demo.presentation.base.fragment.BaseMvvmFragment
import com.ttenushko.mvvm.demo.presentation.screen.places.PlacesViewModel.ViewState
import com.ttenushko.mvvm.demo.presentation.utils.PlaceAdapter
import com.ttenushko.mvvm.demo.presentation.utils.dagger.findDependency
import kotlinx.coroutines.launch
import javax.inject.Inject


class PlacesFragment : BaseMvvmFragment<PlacesViewModel.State, PlacesViewModel>() {

    @Inject
    lateinit var vmProvider: (@JvmSuppressWildcards BaseFragment, @JvmSuppressWildcards PlacesViewModel.State?) -> @JvmSuppressWildcards PlacesViewModel

    @Inject
    lateinit var viewModelStatePersistence: ViewModelStatePersistence<@JvmSuppressWildcards PlacesViewModel.State>
    private var viewBinding: FragmentPlacesBinding? = null
    private val placeAdapter = PlaceAdapter(
        object : PlaceAdapter.Callback {
            override fun onItemClicked(place: Place) {
                viewModel.placeClicked(place)
            }
        }
    )

    override fun onCreate(savedInstanceState: Bundle?) {

        DaggerPlacesFragmentComponent.builder()
            .placesFragmentDependencies(findDependency())
            .placesFragmentModule(PlacesFragmentModule())
            .build()
            .inject(this)

        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View =
        FragmentPlacesBinding.inflate(inflater, container, false).let {
            viewBinding = it
            it.root
        }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewBinding!!.layoutContent.btnAddPlace.setOnClickListener { viewModel.addPlaceButtonClicked() }
        viewBinding!!.layoutContent.apply {
            placeList.adapter = placeAdapter
            placeList.layoutManager =
                LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.viewState.collect { viewState ->
                when (viewState) {
                    is ViewState.NoContent -> {
                        viewBinding!!.layoutContent.root.visibility = View.GONE
                        viewBinding!!.layoutError.root.visibility = View.GONE
                        placeAdapter.clear()
                    }
                    is ViewState.HasContent -> {
                        viewBinding!!.layoutContent.root.visibility = View.VISIBLE
                        viewBinding!!.layoutContent.layoutContentFilled.visibility =
                            if (viewState.places.isNotEmpty()) View.VISIBLE else View.GONE
                        viewBinding!!.layoutContent.layoutContentEmpty.visibility =
                            if (viewState.places.isEmpty()) View.VISIBLE else View.GONE
                        viewBinding!!.layoutError.root.visibility = View.GONE
                        placeAdapter.set(viewState.places)
                    }
                    is ViewState.Error -> {
                        viewBinding!!.layoutContent.root.visibility = View.GONE
                        viewBinding!!.layoutError.root.visibility = View.VISIBLE
                        placeAdapter.clear()
                    }
                }
            }
        }
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.isLoading.collect { isLoading ->
                viewBinding!!.layoutLoading.root.visibility =
                    if (isLoading) View.VISIBLE else View.GONE
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        viewBinding?.layoutContent?.placeList?.adapter = null
        viewBinding = null
    }

    override fun provideViewModel(savedState: PlacesViewModel.State?): PlacesViewModel =
        vmProvider(this, savedState)

    override fun provideViewModelStatePersistence(): ViewModelStatePersistence<PlacesViewModel.State> =
        viewModelStatePersistence
}