package br.com.dcarv.arcmovies.domain.abstraction

import br.com.dcarv.arcmovies.domain.model.Country
import io.reactivex.Observable

/**
 * @author Danilo Carvalho
 */
interface ICountriesListProvider {
    fun listCountries(): Observable<List<Country>>
}