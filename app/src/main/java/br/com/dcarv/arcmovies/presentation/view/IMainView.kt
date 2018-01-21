package br.com.dcarv.arcmovies.presentation.view

import br.com.dcarv.arcmovies.domain.model.UpcomingMoviesList

/**
 * A view for showing upcoming movies
 *
 * @author Danilo Carvalho
 */
interface IMainView : IView {
    fun onUpcomingMoviesListReady(upcomingMoviesList: UpcomingMoviesList, reset: Boolean = false)
    fun onUpcomingMoviesListError(page: Long, error: Throwable)
}