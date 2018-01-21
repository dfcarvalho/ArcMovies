package br.com.dcarv.arcmovies.data.tmdb

import br.com.dcarv.arcmovies.domain.abstraction.ITranslationsProvider
import io.reactivex.Observable

/**
 * An implementation of ITranslationsListProvider that returns available translations from the TMDB API
 */
class TMDBTranslationsProvider(
    private val apiKey: String,
    private val service: TMDBService
) : ITranslationsProvider {
    override fun listTranslations(): Observable<List<String>> {
        return service.getTranslations(apiKey)
    }
}