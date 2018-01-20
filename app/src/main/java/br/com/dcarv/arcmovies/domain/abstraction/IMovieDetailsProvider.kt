package br.com.dcarv.arcmovies.domain.abstraction

import br.com.dcarv.arcmovies.domain.model.Movie
import io.reactivex.Observable

/**
 * Created by dfcarvalho on 15/01/18.
 */
interface IMovieDetailsProvider {
    fun getMovieDetails(movieId: Long, language: String = "pt-BR"): Observable<Movie>
}