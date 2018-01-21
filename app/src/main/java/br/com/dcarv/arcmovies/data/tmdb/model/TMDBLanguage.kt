package br.com.dcarv.arcmovies.data.tmdb.model

/**
 * Represents languages returned by the TMDB API
 *
 * @author Danilo Carvalho
 */
data class TMDBLanguage(
    val iso_639_1: String,
    val english_name: String,
    val name: String
)