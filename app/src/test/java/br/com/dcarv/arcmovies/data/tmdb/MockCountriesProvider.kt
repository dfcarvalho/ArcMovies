package br.com.dcarv.arcmovies.data.tmdb

import br.com.dcarv.arcmovies.domain.abstraction.ICountriesListProvider
import br.com.dcarv.arcmovies.domain.model.Country
import io.reactivex.Observable

/**
 * Created by dfcarvalho on 20/01/18.
 */
class MockCountriesProvider : ICountriesListProvider {
    override fun listCountries(): Observable<List<Country>> {
        return Observable.create { emitter ->
            emitter.onNext(
                listOf(
                    Country(
                        abbr= "BR",
                        name = "Brazil"
                    ),
                    Country(
                        abbr = "US",
                        name = "United States"
                    )
                )
            )
            emitter.onComplete()
        }
    }
}