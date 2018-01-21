package br.com.dcarv.arcmovies.domain.usecase

import br.com.dcarv.arcmovies.domain.abstraction.ICountriesListProvider
import br.com.dcarv.arcmovies.domain.model.Country
import io.reactivex.Observable

/**
 * Returns a list of contries or regions sorted by name
 *
 * @author Danilo Carvalho
 */
class GetCountriesListUseCase(
    private val provider: ICountriesListProvider
) : IUseCase<List<Country>>() {
    private var observable: Observable<List<Country>>? = null

    override fun createObservable(): Observable<List<Country>> {
        if (observable == null) {
            observable = provider.listCountries()
                .map { it.sortedBy { it.name } }
                .cache()
        }

        return observable!!
    }
}