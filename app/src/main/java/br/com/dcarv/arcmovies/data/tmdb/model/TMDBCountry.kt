package br.com.dcarv.arcmovies.data.tmdb.model

/**
 * Represents countries or region returned by the TMDB API
 *
 * @author Danilo Carvalho
 */
data class TMDBCountry(
    val iso_3166_1: String,
    val english_name: String
)