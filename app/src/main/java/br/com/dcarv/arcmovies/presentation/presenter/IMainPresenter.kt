package br.com.dcarv.arcmovies.presentation.presenter

import br.com.dcarv.arcmovies.domain.model.Movie

/**
 * Presenter for the main view (upcoming movies list view)
 *
 * @author Danilo Carvalho
 */
interface IMainPresenter : IPresenter {
    var totalMoviesCount: Long
    var movies: List<Movie>
    var lastLoadedPage: Long
    var language: String
    var region: String
    fun getUpcomingMoviesList(page: Long = 1, shouldReset: Boolean = false)
}