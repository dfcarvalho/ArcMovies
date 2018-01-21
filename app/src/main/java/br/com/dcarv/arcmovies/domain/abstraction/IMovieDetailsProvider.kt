package br.com.dcarv.arcmovies.domain.abstraction

import br.com.dcarv.arcmovies.domain.model.Movie
import io.reactivex.Observable

/**
 * @author Danilo Carvalho
 */
interface IMovieDetailsProvider {
    fun getMovieDetails(movieId: Long, language: String = "pt-BR"): Observable<Movie>
}