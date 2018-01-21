package br.com.dcarv.arcmovies.domain.model

import java.util.*

/**
 * Represents movies in the app
 *
 * @author Danilo Carvalho
 */
data class Movie(
        val id: Long,
        val title: String,
        val overview: String?,
        val posterPath: String?,
        val backdropPath: String?,
        var genres: List<Genre>?,
        val genreIds: List<Long>?,
        val releaseDate: Date?
)