package br.com.dcarv.arcmovies.domain.model


/**
 * Created by dfcarvalho on 15/01/18.
 */
data class UpcomingMoviesList(
        val page: Long,
        val results: List<Movie>,
        val maxDate: String?,
        val minDate: String?,
        val totalPages: Long,
        val totalResults: Long
)