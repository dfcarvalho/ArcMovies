package br.com.dcarv.arcmovies.presentation.view

import br.com.dcarv.arcmovies.domain.model.Movie

/**
 * A View for showing a movie's details
 *
 * @author Danilo Carvalho
 */
interface IMovieDetailsView : IView {
    fun onMovieDetailsReady(movie: Movie)
    fun onMovieDetailsError(movieId: Long, error: Throwable)
}