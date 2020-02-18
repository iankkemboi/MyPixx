package daggerinjections.components

import dagger.Component
import daggerinjections.module.NetworkModule
import ui.ImagesListViewModel
import javax.inject.Singleton

/**
 * Component providing inject() methods for presenters.
 */
@Singleton
@Component(modules = [(NetworkModule::class)])
interface ViewModelInjector {
    /**
     * Injects required dependencies into the specified PostListViewModel.
     * @param postListViewModel PostListViewModel in which to inject the dependencies
     */
    fun inject(postListViewModel: ImagesListViewModel)

    @Component.Builder
    interface Builder {
        fun build(): ViewModelInjector

        fun networkModule(networkModule: NetworkModule): Builder
    }
}