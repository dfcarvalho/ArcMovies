package br.com.dcarv.arcmovies.domain.abstraction

import br.com.dcarv.arcmovies.domain.model.UpcomingMoviesList
import io.reactivex.Observable

/**
 * Created by dfcarvalho on 15/01/18.
 */
interface IUpcomingMoviesListProvider {
    fun listUpcomingMovies(language: String = "pt-BR", page: Long = 1, region: String = "BR") : Observable<UpcomingMoviesList>
}