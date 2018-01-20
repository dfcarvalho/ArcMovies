package br.com.dcarv.arcmovies.data.tmdb

import br.com.dcarv.arcmovies.data.tmdb.model.TMDBMovieMapper
import br.com.dcarv.arcmovies.domain.model.Genre
import br.com.dcarv.arcmovies.domain.model.Movie
import org.hamcrest.CoreMatchers
import org.junit.Assert.assertThat
import org.junit.Test

/**
 * Created by dfcarvalho on 15/01/18.
 */
class TMDBMovieDetailsProviderTest {
    @Test
    fun getMovieDetails_success() {
        val provider = TMDBMovieDetailsProvider(
                "MOCK_API_KEY",
                MockService(),
                mapper = TMDBMovieMapper
        )

        val testObserver = provider.getMovieDetails(1).test()


        testObserver.assertComplete()
        testObserver.assertNoErrors()
        testObserver.assertValueCount(1)
        assertThat(
                testObserver.values(),
                CoreMatchers.hasItems(Movie(
                        id = 1,
                        title = "Harry Potter e a Pedra Filosofal",
                        overview = "Harry Potter descobre que é um bruxo.",
                        posterPath = "some/path/to/image.png",
                        backdropPath = "another/path/to/image.png",
                        genres = listOf(Genre(id = 1, name = "Fantasia")),
                        genreIds = null,
                        releaseDate = "2001-11-23"
                ))
        )
    }
}