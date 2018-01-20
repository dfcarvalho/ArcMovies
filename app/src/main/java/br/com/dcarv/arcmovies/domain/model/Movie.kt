package br.com.dcarv.arcmovies.domain.model

import java.util.*

/**
 * Created by dfcarvalho on 15/01/18.
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