package br.com.dcarv.arcmovies.data.tmdb

import br.com.dcarv.arcmovies.domain.abstraction.IUpcomingMoviesListProvider
import br.com.dcarv.arcmovies.domain.model.Genre
import br.com.dcarv.arcmovies.domain.model.Movie
import br.com.dcarv.arcmovies.domain.model.UpcomingMoviesList
import io.reactivex.Observable
import java.util.*

/**
 * Created by dfcarvalho on 20/01/18.
 */
class MockUpcomingMoviesListProvider : IUpcomingMoviesListProvider {
    override fun listUpcomingMovies(
        language: String,
        page: Long,
        region: String
    ): Observable<UpcomingMoviesList> {
        return Observable.create { emitter ->
            emitter.onNext(
                UpcomingMoviesList(
                    page = 1,
                    results = listOf(
                        Movie(
                            id = 1,
                            title = "Harry Potter e a Pedra Filosofal",
                            overview = "Harry Potter descobre que é um bruxo.",
                            posterPath = "/some/path/to/image.png",
                            backdropPath = "/another/path/to/image.png",
                            genres = null,
                            genreIds = listOf(1L),
                            releaseDate = GregorianCalendar(2001, 10, 23).time
                        )
                    ),
                    maxDate = "2017-01-15",
                    minDate = "2017-03-01",
                    totalPages = 10,
                    totalResults = 300
                )
            )
            emitter.onComplete()
        }
    }
}