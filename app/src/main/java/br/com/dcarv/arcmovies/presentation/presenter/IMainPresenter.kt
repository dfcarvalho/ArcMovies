package br.com.dcarv.arcmovies.presentation.presenter

import br.com.dcarv.arcmovies.domain.model.Movie

/**
 * Created by dfcarvalho on 15/01/18.
 */
interface IMainPresenter : IPresenter {
    fun getUpcomingMoviesList(page: Long = 1)
    var totalMoviesCount: Long
    var movies: List<Movie>
    var lastLoadedPage: Long
}