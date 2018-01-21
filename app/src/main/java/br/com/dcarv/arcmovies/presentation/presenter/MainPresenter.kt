package br.com.dcarv.arcmovies.presentation.presenter

import br.com.dcarv.arcmovies.domain.abstraction.*
import br.com.dcarv.arcmovies.domain.model.Movie
import br.com.dcarv.arcmovies.domain.usecase.GetCountriesListUseCase
import br.com.dcarv.arcmovies.domain.usecase.GetGenresUseCase
import br.com.dcarv.arcmovies.domain.usecase.GetLanguagesListUseCase
import br.com.dcarv.arcmovies.domain.usecase.GetUpcomingMoviesListUseCase
import br.com.dcarv.arcmovies.presentation.view.IMainView
import br.com.dcarv.arcmovies.presentation.view.IView

/**
 * An implementation of IMainPresenter for Android
 */
class MainPresenter(
    private val provider: IUpcomingMoviesListProvider,
    genreProvider: IGenreProvider,
    countriesProvider: ICountriesListProvider,
    private val languagesProvider: ILanguagesListProvider,
    private val translationsProvider: ITranslationsProvider,
    private val preferencesProvider: IPreferencesProvider
) : IMainPresenter {
    private var view: IMainView? = null

    override var region: String = preferencesProvider.getCountry()
        set(value) {
            if (value != field) {
                field = value
                preferencesProvider.setCountry(value)
            }
        }
    override var language: String = preferencesProvider.getLanguage()
        set(value) {
            if (value != field) {
                field = value
                preferencesProvider.setLanguage(value)
            }
        }

    private var getUpcomingMoviesUseCase: GetUpcomingMoviesListUseCase? = null
    private val getGenreUseCase = GetGenresUseCase(genreProvider, language)
    private val getCountriesListUseCase = GetCountriesListUseCase(countriesProvider)
    private val getLanguagesListUseCase = GetLanguagesListUseCase(languagesProvider, translationsProvider)
    override var totalMoviesCount: Long = 0
    override var movies: List<Movie> = listOf()
    override var lastLoadedPage: Long = 0

    override fun getUpcomingMoviesList(page: Long, shouldReset: Boolean) {
        disposeObservable()

        if (shouldReset) {
            reset()
        }

        getUpcomingMoviesUseCase = GetUpcomingMoviesListUseCase(
            provider,
            getGenreUseCase,
            language = language,
            page = page,
            region = region
        )

        getUpcomingMoviesUseCase?.subscribe(
            onNext = {
                lastLoadedPage = page
                totalMoviesCount = it.totalResults
                movies += it.results
                view?.onUpcomingMoviesListReady(it, reset = shouldReset)
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

    private fun reset() {
        disposeObservable()
        totalMoviesCount = 0
        movies = listOf()
        lastLoadedPage = 0
    }
}
