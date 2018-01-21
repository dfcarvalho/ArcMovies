package br.com.dcarv.arcmovies.presentation.presenter

import br.com.dcarv.arcmovies.domain.model.Country
import br.com.dcarv.arcmovies.domain.model.UpcomingMoviesList
import br.com.dcarv.arcmovies.presentation.view.IMainView

/**
 * Created by dfcarvalho on 20/01/18.
 */
class MockMainView : IMainView {
    var lastLoadedMoviesList: UpcomingMoviesList? = null
    var countries: List<Country>? = null

    override fun onUpcomingMoviesListReady(upcomingMoviesList: UpcomingMoviesList, reset: Boolean) {
        lastLoadedMoviesList = upcomingMoviesList
    }

    override fun onUpcomingMoviesListError(page: Long, error: Throwable) {
        // do nothing
        lastLoadedMoviesList = null
    }
}