package br.com.dcarv.arcmovies.domain.usecase

import br.com.dcarv.arcmovies.TMDB_BACKDROP_URL
import br.com.dcarv.arcmovies.TMDB_POSTER_URL
import br.com.dcarv.arcmovies.domain.abstraction.IUpcomingMoviesListProvider
import br.com.dcarv.arcmovies.domain.model.Genre
import br.com.dcarv.arcmovies.domain.model.UpcomingMoviesList
import io.reactivex.Observable

/**
 * Created by dfcarvalho on 15/01/18.
 */
class GetUpcomingMoviesListUseCase(
        private val provider: IUpcomingMoviesListProvider,
        private val getGenresUseCase: GetGenresUseCase,
        val language: String = "pt-BR",
        val page: Long = 1,
        val region: String = "BR"
) : IUseCase<UpcomingMoviesList>() {
    override fun createObservable(): Observable<UpcomingMoviesList> {
        // get genres list
        return getGenresUseCase.createObservable()
                .flatMap { genres ->
                    // get upcoming movies
                    provider.listUpcomingMovies(language, page, region)
                            .map { movies ->
                                // set genres list based on id
                                movies.results.forEach { movie ->
                                    val movieGenres = mutableListOf<Genre>()
                                    movie.genreIds?.forEach { genreId ->
                                        genres.get(genreId)?.let { movieGenres.add(it) }
                                    }
                                    movie.genres = movieGenres
                                }
                                movies
                            }
                }
    }
}