package br.com.dcarv.arcmovies.data.tmdb

import br.com.dcarv.arcmovies.data.tmdb.model.*
import br.com.dcarv.arcmovies.domain.model.Genre
import io.reactivex.Observable

/**
 * Created by dfcarvalho on 15/01/18.
 */
class MockService : TMDBService {
    override fun listUpcomingMovies(
        apiKey: String,
        language: String,
        page: Long,
        region: String
    ): Observable<TMDBUpcomingResponse> {
        return Observable.create { emitter ->
            emitter.onNext(
                TMDBUpcomingResponse(
                    page = 1,
                    results = listOf(
                        TMDBMovie(
                            id = 1,
                            title = "Harry Potter e a Pedra Filosofal",
                            overview = "Harry Potter descobre que é um bruxo.",
                            poster_path = "/some/path/to/image.png",
                            backdrop_path = "/another/path/to/image.png",
                            genres = null,
                            genre_ids = listOf(1L),
                            release_date = "2001-11-23"
                        )
                    ),
                    dates = TMDBUpcomingDatesRange(
                        maximum = "2017-01-15",
                        minumum = "2017-03-01"
                    ),
                    total_pages = 10,
                    total_results = 300
                )
            )
            emitter.onComplete()
        }
    }

    override fun getMovieDetails(
        movieId: Long,
        apiKey: String,
        language: String
    ): Observable<TMDBMovie> {
        return Observable.create { emitter ->
            emitter.onNext(
                TMDBMovie(
                    id = 1,
                    title = "Harry Potter e a Pedra Filosofal",
                    overview = "Harry Potter descobre que é um bruxo.",
                    poster_path = "/some/path/to/image.png",
                    backdrop_path = "/another/path/to/image.png",
                    genres = listOf(Genre(id = 1, name = "Fantasia")),
                    genre_ids = null,
                    release_date = "2001-11-23"
                )
            )
            emitter.onComplete()
        }
    }

    override fun getGenres(apiKey: String, language: String): Observable<TMDBGenresList> {
        return Observable.create { emitter ->
            emitter.onNext(
                TMDBGenresList(
                    genres = listOf(
                        TMDBGenre(
                            id = 1,
                            name = "Fantasia"
                        ), TMDBGenre(
                            id = 2,
                            name = "Policial"
                        ), TMDBGenre
                            (
                            id = 3,
                            name = "Terror"
                        )
                    )
                )
            )
            emitter.onComplete()
        }
    }

    override fun getCountries(apiKey: String): Observable<List<TMDBCountry>> {
        return Observable.create { emitter ->
            emitter.onNext(
                listOf(
                    TMDBCountry(
                        iso_3166_1 = "BR",
                        english_name = "Brazil"
                    ),
                    TMDBCountry(
                        iso_3166_1 = "US",
                        english_name = "United States"
                    )
                )
            )
            emitter.onComplete()
        }
    }

    override fun getLanguages(apiKey: String): Observable<List<TMDBLanguage>> {
        return Observable.create { emitter ->
            emitter.onNext(
                listOf(
                    TMDBLanguage(
                        iso_639_1 = "pt-BR",
                        english_name = "Portuguese (Brazil)",
                        name = "Português (Brasil)"
                    ),
                    TMDBLanguage(
                        iso_639_1 = "en-US",
                        english_name = "English (US)",
                        name = "English (US)"
                    )
                )
            )
            emitter.onComplete()
        }
    }

    override fun getTranslations(apiKey: String): Observable<List<String>> {
        return Observable.create { emitter ->
            emitter.onNext(listOf("pt-BR", "en-US", "de-DE"))
            emitter.onComplete()
        }
    }
}