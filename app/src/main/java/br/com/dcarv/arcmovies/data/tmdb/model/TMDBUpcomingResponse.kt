package br.com.dcarv.arcmovies.data.tmdb.model

/**
 * Created by dfcarvalho on 15/01/18.
 */
data class TMDBUpcomingResponse(
        val page: Long,
        val results: List<TMDBMovie>,
        val dates: TMDBUpcomingDatesRange,
        val total_pages: Long,
        val total_results: Long
)