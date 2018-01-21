package br.com.dcarv.arcmovies.domain.abstraction

import br.com.dcarv.arcmovies.domain.model.Country

/**
 * @author Danilo Carvalho
 */
interface ICountryMapper<in T> {
    fun toCountry(obj: T): Country
}