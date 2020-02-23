package base

import androidx.lifecycle.ViewModel
import daggerinjections.components.DaggerViewModelInjector
import daggerinjections.components.ViewModelInjector
import daggerinjections.module.NetworkModule
import io.reactivex.Single
import io.reactivex.disposables.Disposable
import ui.ImagesListViewModel

abstract class BaseViewModel: ViewModel(){
    private val injector: ViewModelInjector = DaggerViewModelInjector
        .builder()
        .networkModule(NetworkModule)
        .build()

    init {
        inject()
    }

    /**
     * Injects the required dependencies
     */
    private fun inject() {
        when (this) {
            is ImagesListViewModel -> injector.inject(this)
        }
    }


}