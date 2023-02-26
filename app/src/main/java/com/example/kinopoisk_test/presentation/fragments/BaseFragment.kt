package com.example.kinopoisk_test.presentation.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.viewbinding.ViewBinding
import com.example.kinopoisk_test.app.App
import com.example.kinopoisk_test.common.logger.MyLogger
import com.example.kinopoisk_test.databinding.ActivityMainBinding
import com.example.kinopoisk_test.presentation.activity.MainActivity
import com.github.terrakok.cicerone.Router
import io.reactivex.rxjava3.disposables.CompositeDisposable

abstract class BaseFragment <T : ViewBinding, VM: BaseViewModel> : Fragment() {

    protected abstract val inflater: (LayoutInflater, ViewGroup?, Boolean) -> T
    protected var binding: T? = null
    protected var viewModel: VM? = null
    protected abstract val viewModelClass: Class<VM>
    protected var activityBinding: ActivityMainBinding? = null
    protected var router: Router? = null

    protected abstract val screenName: String

    protected var compositeDisposable: CompositeDisposable? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        if (binding == null) {
            binding = inflater(inflater, container, false)
        }
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (viewModel == null) {
            viewModel = ViewModelProvider(requireActivity())[viewModelClass]
            if (activityBinding == null) {
                activityBinding = (requireActivity() as? MainActivity)?.binding
            }
            if (router == null) {
                router = (requireContext().applicationContext as? App)?.router
            }
            if (compositeDisposable == null) {
                compositeDisposable = CompositeDisposable()
            }

            initStartValues()
            initUI()
            initRecycler()
            initObservers()
            initButtons()
        }
    }

    protected open fun initUI() {
        MyLogger.log("initUI -> $screenName")
    }

    protected open fun initObservers() {
        MyLogger.log("initObservers -> $screenName")
    }

    protected open fun initStartValues() {
        MyLogger.log("initStartValues -> $screenName")
    }

    protected open fun initButtons() {
        MyLogger.log("initButtons -> $screenName")
    }

    protected open fun initRecycler() {
        MyLogger.log("initRecycler -> $screenName")
    }

    protected abstract fun showToolBar()
    protected abstract fun closeToolBar()

    protected abstract fun showLoadingScreen()
    protected abstract fun closeLoadingScreen()

    protected abstract fun showErrorScreen()
    protected abstract fun closeErrorScreen()

    protected abstract fun closeOtherViews()


//    private fun getScreenOrientation(): Int {
//        return when (resources.configuration.orientation) {
//            Configuration.ORIENTATION_LANDSCAPE -> Configuration.ORIENTATION_LANDSCAPE
//            else -> Configuration.ORIENTATION_PORTRAIT
//        }
//    }

    override fun onDetach() {
        binding = null
        viewModel = null
        router = null
        activityBinding = null
        compositeDisposable?.dispose()
        compositeDisposable?.clear()
        compositeDisposable = null
        super.onDetach()
    }

}