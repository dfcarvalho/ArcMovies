package br.com.dcarv.arcmovies.presentation.presenter

import br.com.dcarv.arcmovies.domain.abstraction.IGenreProvider
import br.com.dcarv.arcmovies.domain.abstraction.IUpcomingMoviesListProvider
import br.com.dcarv.arcmovies.domain.model.Movie
import br.com.dcarv.arcmovies.domain.usecase.GetGenresUseCase
import br.com.dcarv.arcmovies.domain.usecase.GetUpcomingMoviesListUseCase
import br.com.dcarv.arcmovies.presentation.view.IMainView
import br.com.dcarv.arcmovies.presentation.view.IView

/**
 * Created by dfcarvalho on 15/01/18.
 */

class MainPresenter(
        private val provider: IUpcomingMoviesListProvider,
        genreProvider: IGenreProvider
) : IMainPresenter {
    private var view: IMainView? = null
    var language: String = "pt-BR"
    var region: String = "BR"
    private var getUpcomingMoviesUseCase: GetUpcomingMoviesListUseCase? = null
    private val getGenreUseCase = GetGenresUseCase(genreProvider, language)
    override var totalMoviesCount: Long = 0
    override var movies: List<Movie> = listOf()
    override var lastLoadedPage: Long = 0

    override fun getUpcomingMoviesList(page: Long) {
        disposeObservable()

        getUpcomingMoviesUseCase = GetUpcomingMoviesListUseCase(
                provider,
                getGenreUseCase,
                language = language,
                page = page,
                region = region)

        getUpcomingMoviesUseCase?.subscribe(
                onNext = {
                    lastLoadedPage = page
                    totalMoviesCount = it.totalResults
                    movies += it.results
                    view?.onUpcomingMoviesListReady(it)
                },
                onError = { view?.onUpcomingMoviesListError(page, it) }
        )
    }

    override fun attachView(view: IView) {
        this.view = view as IMainView
    }

    override fun detachView() {
        view = null
        disposeObservable()
    }

    private fun disposeObservable() {
        getUpcomingMoviesUseCase?.let {
            if (!it.isUnsubscribed()) {
                it.unsubscribe()
            }
        }
    }
}