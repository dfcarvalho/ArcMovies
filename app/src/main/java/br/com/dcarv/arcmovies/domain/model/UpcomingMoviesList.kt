package br.com.dcarv.arcmovies.domain.model


/**
 * Represents upcoming movies lists in the app
 *
 * @author Danilo Carvalho
 */
data class UpcomingMoviesList(
        val page: Long,
        val results: List<Movie>,
        val maxDate: String?,
        val minDate: String?,
        val totalPages: Long,
        val totalResults: Long
)