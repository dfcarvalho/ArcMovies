package br.com.dcarv.arcmovies.data.tmdb.model

import br.com.dcarv.arcmovies.domain.model.Genre
import br.com.dcarv.arcmovies.domain.model.Movie
import br.com.dcarv.arcmovies.domain.model.UpcomingMoviesList
import org.junit.Test

import org.junit.Assert.*

/**
 * Created by dfcarvalho on 15/01/18.
 */
class TMDBUpcomingMoviesListMapperTest {
    @Test
    fun toUpcomingMoviesList_success() {
        val tmdbList = TMDBUpcomingResponse(
                page = 1,
                results = listOf(TMDBMovie(
                        id = 1,
                        title = "Harry Potter e a Pedra Filosofal",
                        overview = "Harry Potter descobre que é um bruxo.",
                        poster_path = "some/path/to/image.png",
                        backdrop_path = "another/path/to/image.png",
                        genres = listOf(Genre(id = 1, name = "Fantasia")),
                        genre_ids = null,
                        release_date = "2001-11-23"
                )),
                dates = TMDBUpcomingDatesRange(
                        maximum = "2017-01-15",
                        minumum = "2017-03-01"
                ),
                total_pages = 10,
                total_results = 300
        )

        val list = TMDBUpcomingMoviesListMapper.toUpcomingMoviesList(tmdbList)

        val expected = UpcomingMoviesList(
                page = 1,
                results = listOf(Movie(
                        id = 1,
                        title = "Harry Potter e a Pedra Filosofal",
                        overview = "Harry Potter descobre que é um bruxo.",
                        posterPath = "some/path/to/image.png",
                        backdropPath = "another/path/to/image.png",
                        genres = listOf(Genre(id = 1, name = "Fantasia")),
                        genreIds = null,
                        releaseDate = "2001-11-23"
                )),
                maxDate = "2017-01-15",
                minDate = "2017-03-01",
                totalPages = 10,
                totalResults = 300
        )

        assertEquals(expected, list)
    }
}