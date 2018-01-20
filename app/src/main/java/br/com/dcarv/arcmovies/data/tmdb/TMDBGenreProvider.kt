package br.com.dcarv.arcmovies.data.tmdb

import br.com.dcarv.arcmovies.data.tmdb.model.TMDBGenre
import br.com.dcarv.arcmovies.domain.abstraction.IGenreMapper
import br.com.dcarv.arcmovies.domain.abstraction.IGenreProvider
import br.com.dcarv.arcmovies.domain.model.Genre
import io.reactivex.Observable

/**
 * Created by dfcarvalho on 16/01/18.
 */
class TMDBGenreProvider(
        private val apiKey: String,
        private val service: TMDBService,
        private val mapper: IGenreMapper<TMDBGenre>
) : IGenreProvider {
    override fun listGenres(language: String): Observable<List<Genre>> {
        return service.getGenres(apiKey, language = language)
                .map { it.genres.map { mapper.toGenre(it) } }
    }
}