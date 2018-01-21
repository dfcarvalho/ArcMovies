package br.com.dcarv.arcmovies.domain.abstraction

import br.com.dcarv.arcmovies.domain.model.UpcomingMoviesList

/**
 * @author Danilo Carvalho
 */
interface IUpcomingMoviesListMapper<in T> {
    fun toUpcomingMoviesList(obj: T) : UpcomingMoviesList
}