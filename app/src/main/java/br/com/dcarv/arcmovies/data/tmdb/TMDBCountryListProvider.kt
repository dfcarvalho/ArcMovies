package br.com.dcarv.arcmovies.data.tmdb

import br.com.dcarv.arcmovies.data.tmdb.model.TMDBCountry
import br.com.dcarv.arcmovies.domain.abstraction.ICountriesListProvider
import br.com.dcarv.arcmovies.domain.abstraction.ICountryMapper
import br.com.dcarv.arcmovies.domain.model.Country
import io.reactivex.Observable

/**
 * An ICountriesListProvider that returns countries from the TMDB API
 *
 * @author Danilo Carvalho
 */
class TMDBCountryListProvider(
    private val apiKey: String,
    private val service: TMDBService,
    private val mapper: ICountryMapper<TMDBCountry>
) : ICountriesListProvider {
    override fun listCountries(): Observable<List<Country>> {
        return service.getCountries(apiKey)
            .map { it.map { mapper.toCountry(it) } }
    }
}