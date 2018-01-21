package br.com.dcarv.arcmovies.data.tmdb

import br.com.dcarv.arcmovies.data.tmdb.model.TMDBMovie
import br.com.dcarv.arcmovies.domain.abstraction.IMovieDetailsMapper
import br.com.dcarv.arcmovies.domain.abstraction.IMovieDetailsProvider
import br.com.dcarv.arcmovies.domain.model.Movie
import io.reactivex.Observable

/**
 * An IMovieDetailsProvider that returns movies details from the TMDB API
 *
 * @author Danilo Carvalho
 */
class TMDBMovieDetailsProvider(
        private val apiKey: String,
        private val service: TMDBService,
        private val mapper: IMovieDetailsMapper<TMDBMovie>
) : IMovieDetailsProvider {
    override fun getMovieDetails(movieId: Long, language: String): Observable<Movie> {
        return service.getMovieDetails(movieId, apiKey, language = language)
                // TODO: retryWhen()
                .map { mapper.toMovie(it) }
    }
}