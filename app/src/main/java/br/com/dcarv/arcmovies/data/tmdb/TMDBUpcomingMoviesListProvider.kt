package br.com.dcarv.arcmovies.data.tmdb

import br.com.dcarv.arcmovies.data.tmdb.model.TMDBUpcomingResponse
import br.com.dcarv.arcmovies.domain.abstraction.IUpcomingMoviesListMapper
import br.com.dcarv.arcmovies.domain.abstraction.IUpcomingMoviesListProvider
import br.com.dcarv.arcmovies.domain.model.UpcomingMoviesList
import io.reactivex.Observable

/**
 * An implementation of IUpcomingMoviesListProvider that returns upcoming movies from the TMDB API
 *
 * @author Danilo Carvalho
 */
class TMDBUpcomingMoviesListProvider(
        private val apiKey: String,
        private val service: TMDBService,
        private val mapper: IUpcomingMoviesListMapper<TMDBUpcomingResponse>) : IUpcomingMoviesListProvider {

    override fun listUpcomingMovies(language: String, page: Long, region: String): Observable<UpcomingMoviesList> {
        return service.listUpcomingMovies(apiKey, language = language, page = page, region = region)
                // TODO: retryWhen()
                .map { mapper.toUpcomingMoviesList(it) }
    }
}