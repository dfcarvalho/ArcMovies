package br.com.dcarv.arcmovies.domain.abstraction

import br.com.dcarv.arcmovies.domain.model.Movie

/**
 * Created by dfcarvalho on 15/01/18.
 */
interface IMovieDetailsMapper<in T> {
    fun toMovie(obj: T): Movie
}