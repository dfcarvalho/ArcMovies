package br.com.dcarv.arcmovies.data.tmdb.model

/**
 * Represents upcoming movies list response returned by the TMDB API
 *
 * @author Danilo Carvalho
 */
data class TMDBUpcomingResponse(
        val page: Long,
        val results: List<TMDBMovie>,
        val dates: TMDBUpcomingDatesRange,
        val total_pages: Long,
        val total_results: Long
)