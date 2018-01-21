package br.com.dcarv.arcmovies.presentation.presenter

import br.com.dcarv.arcmovies.domain.model.Movie
import br.com.dcarv.arcmovies.presentation.view.IMovieDetailsView

/**
 * Created by dfcarvalho on 20/01/18.
 */
class MockMovieDetailsView : IMovieDetailsView {
    var movie: Movie? = null

    override fun onMovieDetailsReady(movie: Movie) {
        this.movie = movie
    }

    override fun onMovieDetailsError(movieId: Long, error: Throwable) {
        this.movie = null
    }

}