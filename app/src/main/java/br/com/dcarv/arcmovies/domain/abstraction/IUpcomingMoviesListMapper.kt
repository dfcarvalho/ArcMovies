package br.com.dcarv.arcmovies.domain.abstraction

import br.com.dcarv.arcmovies.domain.model.UpcomingMoviesList

/**
 * Created by dfcarvalho on 15/01/18.
 */
interface IUpcomingMoviesListMapper<in T> {
    fun toUpcomingMoviesList(obj: T) : UpcomingMoviesList
}