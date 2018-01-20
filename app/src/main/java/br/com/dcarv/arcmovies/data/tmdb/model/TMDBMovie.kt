package br.com.dcarv.arcmovies.data.tmdb.model

import br.com.dcarv.arcmovies.domain.model.Genre

/**
 * Created by dfcarvalho on 15/01/18.
 */
data class TMDBMovie(
        val id: Long,
        val title: String,
        val overview: String?,
        val poster_path: String?,
        val backdrop_path: String?,
        val genres: List<Genre>?,
        val genre_ids: List<Long>?,
        val release_date: String
)