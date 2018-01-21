package br.com.dcarv.arcmovies.domain.abstraction

import br.com.dcarv.arcmovies.domain.model.Movie

/**
 * @author Danilo Carvalho
 */
interface IMovieDetailsMapper<in T> {
    fun toMovie(obj: T): Movie
}