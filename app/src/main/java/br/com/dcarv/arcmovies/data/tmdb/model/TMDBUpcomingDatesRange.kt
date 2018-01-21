package br.com.dcarv.arcmovies.data.tmdb.model

/**
 * Represents dates returned by the TMDB API's get upcoming movies endpoint
 *
 * @author Danilo Carvalho
 */
data class TMDBUpcomingDatesRange(
        val maximum: String?,
        val minumum: String?
)