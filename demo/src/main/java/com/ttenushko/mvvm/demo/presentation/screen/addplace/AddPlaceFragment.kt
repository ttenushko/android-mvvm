package com.ttenushko.mvvm.demo.presentation.screen.addplace

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.ttenushko.mvvm.demo.databinding.FragmentAddPlaceBinding
import com.ttenushko.mvvm.demo.di.presentation.screen.AddPlaceFragmentModule
import com.ttenushko.mvvm.demo.di.presentation.screen.DaggerAddPlaceFragmentComponent
import com.ttenushko.mvvm.demo.domain.weather.model.Place
import com.ttenushko.mvvm.demo.presentation.base.fragment.BaseFragment
import com.ttenushko.mvvm.demo.presentation.base.fragment.BaseMvvmFragment
import com.ttenushko.mvvm.demo.presentation.utils.PlaceAdapter
import com.ttenushko.mvvm.demo.presentation.utils.dagger.findDependency
import kotlinx.coroutines.launch
import javax.inject.Inject

class AddPlaceFragment : BaseMvvmFragment<AddPlaceViewModel.State, AddPlaceViewModel>() {

    @Inject
    lateinit var vmProvider: (@JvmSuppressWildcards BaseFragment, @JvmSuppressWildcards AddPlaceViewModel.State?) -> @JvmSuppressWildcards AddPlaceViewModel

    @Inject
    lateinit var viewModelStatePersistence: ViewModelStatePersistence<@JvmSuppressWildcards AddPlaceViewModel.State>
    private var viewBinding: FragmentAddPlaceBinding? = null
    private val placeAdapter = PlaceAdapter(
        object : PlaceAdapter.Callback {
            override fun onItemClicked(place: Place) {
                viewModel.placeClicked(place)
            }
        }
    )

    override fun onCreate(savedInstanceState: Bundle?) {

        DaggerAddPlaceFragmentComponent.builder()
            .addPlaceFragmentDependencies(findDependency())
            .addPlaceFragmentModule(
                AddPlaceFragmentModule(
                    requireArguments().getString(ARG_SEARCH, "")
                )
            )
            .build()
            .inject(this)

        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View =
        FragmentAddPlaceBinding.inflate(inflater, container, false).let {
            viewBinding = it
            it.root
        }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewBinding!!.placeList.apply {
            adapter = placeAdapter
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        }
        viewBinding!!.toolbar.searchView.apply {
            isFocusable = true
            isIconified = false
            setOnQueryTextListener(searchTextWatcher)
            setIconifiedByDefault(false)
            requestFocusFromTouch()
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.viewState.collect { state ->
                viewBinding!!.toolbar.searchView.apply {
                    if (query.toString() != state.search) {
                        setOnQueryTextListener(null)
                        setQuery(state.search, true)
                        setOnQueryTextListener(searchTextWatcher)
                    }
                }
                viewBinding!!.progress.visibility =
                    if (state.isSearching) View.VISIBLE else View.GONE
                when (state.searchResult) {
                    is AddPlaceViewModel.ViewState.SearchResult.Success ->
                        placeAdapter.set(state.searchResult.places)
                    null, is AddPlaceViewModel.ViewState.SearchResult.Failure ->
                        placeAdapter.clear()
                }
                when {
                    state.isShowSearchPrompt -> {
                        viewBinding!!.message.visibility = View.VISIBLE
                        viewBinding!!.message.text = "Start typing text to search"
                    }
                    state.isShowSearchNoResultsPrompt -> {
                        viewBinding!!.message.visibility = View.VISIBLE
                        viewBinding!!.message.text = "Nothing found"
                    }
                    state.isShowSearchErrorPrompt -> {
                        viewBinding!!.message.visibility = View.VISIBLE
                        viewBinding!!.message.text = "Error occurred"
                    }
                    else -> {
                        viewBinding!!.message.visibility = View.GONE
                    }
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        viewBinding?.toolbar?.searchView?.setOnQueryTextListener(null)
        viewBinding?.placeList?.adapter = null
        viewBinding = null
    }

    override fun provideViewModel(savedState: AddPlaceViewModel.State?): AddPlaceViewModel =
        vmProvider(this, savedState)

    override fun provideViewModelStatePersistence(): ViewModelStatePersistence<AddPlaceViewModel.State> =
        viewModelStatePersistence

    private val searchTextWatcher = object : SearchView.OnQueryTextListener {
        override fun onQueryTextSubmit(text: String): Boolean {
            return false
        }

        override fun onQueryTextChange(text: String): Boolean {
            viewModel.searchChanged(text)
            return true
        }
    }


    companion object {
        private const val ARG_SEARCH = "search"
        fun args(search: String): Bundle =
            Bundle().apply {
                putString(ARG_SEARCH, search)
            }
    }
}