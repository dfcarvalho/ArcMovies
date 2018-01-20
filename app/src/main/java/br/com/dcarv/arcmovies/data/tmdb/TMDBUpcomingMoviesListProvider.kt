package br.com.dcarv.arcmovies.data.tmdb

import br.com.dcarv.arcmovies.data.tmdb.model.TMDBUpcomingResponse
import br.com.dcarv.arcmovies.domain.abstraction.IUpcomingMoviesListMapper
import br.com.dcarv.arcmovies.domain.abstraction.IUpcomingMoviesListProvider
import br.com.dcarv.arcmovies.domain.model.UpcomingMoviesList
import io.reactivex.Observable

/**
 * Created by dfcarvalho on 15/01/18.
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