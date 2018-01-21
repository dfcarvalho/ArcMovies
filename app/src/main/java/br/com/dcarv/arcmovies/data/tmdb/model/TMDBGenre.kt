package br.com.dcarv.arcmovies.data.tmdb.model

/**
 * Represents genres returned by the TMDB API
 *
 * @author Danilo Carvalho
 */
data class TMDBGenre(
        val id: Long,
        val name: String
)