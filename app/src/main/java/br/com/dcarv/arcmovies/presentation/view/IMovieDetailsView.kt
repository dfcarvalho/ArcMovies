package br.com.dcarv.arcmovies.presentation.view

import br.com.dcarv.arcmovies.domain.model.Movie

/**
 * Created by dfcarvalho on 17/01/18.
 */
interface IMovieDetailsView : IView {
    fun onMovieDetailsReady(movie: Movie)
    fun onMovieDetailsError(movieId: Long, error: Throwable)
}