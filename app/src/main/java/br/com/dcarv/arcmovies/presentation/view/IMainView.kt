package br.com.dcarv.arcmovies.presentation.view

import br.com.dcarv.arcmovies.domain.model.UpcomingMoviesList

/**
 * Created by dfcarvalho on 16/01/18.
 */
interface IMainView : IView {
    fun onUpcomingMoviesListReady(upcomingMoviesList: UpcomingMoviesList)
    fun onUpcomingMoviesListError(page: Long, error: Throwable)
}