package br.com.dcarv.arcmovies.data.tmdb

import br.com.dcarv.arcmovies.domain.abstraction.IMovieDetailsProvider
import br.com.dcarv.arcmovies.domain.model.Genre
import br.com.dcarv.arcmovies.domain.model.Movie
import io.reactivex.Observable
import java.util.*

/**
 * Created by dfcarvalho on 20/01/18.
 */
class MockMovieDetailsProvider : IMovieDetailsProvider {
    override fun getMovieDetails(movieId: Long, language: String): Observable<Movie> {
        return Observable.create { emitter ->
            emitter.onNext(
                Movie(
                    id = 1,
                    title = "Harry Potter e a Pedra Filosofal",
                    overview = "Harry Potter descobre que é um bruxo.",
                    posterPath = "/some/path/to/image.png",
                    backdropPath = "/another/path/to/image.png",
                    genres = listOf(Genre(id = 1, name = "Fantasia")),
                    genreIds = null,
                    releaseDate = GregorianCalendar(2001, 10, 23).time
                )
            )
            emitter.onComplete()
        }
    }

}