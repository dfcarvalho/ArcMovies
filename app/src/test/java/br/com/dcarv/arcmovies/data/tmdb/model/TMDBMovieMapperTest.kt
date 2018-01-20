package br.com.dcarv.arcmovies.data.tmdb.model

import br.com.dcarv.arcmovies.domain.model.Genre
import br.com.dcarv.arcmovies.domain.model.Movie
import org.junit.Test

import org.junit.Assert.*

/**
 * Created by dfcarvalho on 15/01/18.
 */
class TMDBMovieMapperTest {
    @Test
    fun toMovie_success() {
        val tmdbMovie = TMDBMovie(
                id = 1,
                title = "Harry Potter e a Pedra Filosofal",
                overview = "Harry Potter descobre que é um bruxo.",
                poster_path = "some/path/to/image.png",
                backdrop_path = "another/path/to/image.png",
                genres = listOf(Genre(id = 1, name = "Fantasia")),
                genre_ids = null,
                release_date = "2001-11-23"
        )

        val movie = TMDBMovieMapper.toMovie(tmdbMovie)

        val expected = Movie(
                id = 1,
                title = "Harry Potter e a Pedra Filosofal",
                overview = "Harry Potter descobre que é um bruxo.",
                posterPath = "some/path/to/image.png",
                backdropPath = "another/path/to/image.png",
                genres = listOf(Genre(id = 1, name = "Fantasia")),
                genreIds = null,
                releaseDate = "2001-11-23"
        )

        assertEquals(expected, movie)
    }
}