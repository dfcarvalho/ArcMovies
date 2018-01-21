package br.com.dcarv.arcmovies.data.tmdb

import br.com.dcarv.arcmovies.data.tmdb.model.TMDBLanguage
import br.com.dcarv.arcmovies.domain.abstraction.ILanguageMapper
import br.com.dcarv.arcmovies.domain.abstraction.ILanguagesListProvider
import br.com.dcarv.arcmovies.domain.model.Language
import io.reactivex.Observable

/**
 * An ILanguagesListProvider that returns languages from the TMDB API
 *
 * @author Danilo Carvalho
 */
class TMDBLanguagesListProvider(
    private val apiKey: String,
    private val service: TMDBService,
    private val mapper: ILanguageMapper<TMDBLanguage>
) : ILanguagesListProvider {
    override fun listLanguages(): Observable<List<Language>> {
        return service.getLanguages(apiKey)
            .map { it.map { mapper.toLanguage(it) } }
    }
}