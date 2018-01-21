package br.com.dcarv.arcmovies.data.tmdb.model

import br.com.dcarv.arcmovies.domain.abstraction.ICountryMapper
import br.com.dcarv.arcmovies.domain.model.Country

/**
 * Maps {@link br.com.dcarv.arcmovies.data.tmdb.model.TMDBCountry} to
 * {@link br.com.dcarv.arcmovies.domain.model.Country}
 *
 * @author Danilo Carvalho
 */
object TMDBCountryMapper : ICountryMapper<TMDBCountry> {
    override fun toCountry(obj: TMDBCountry) = Country(
        abbr = obj.iso_3166_1,
        name = obj.english_name
    )
}