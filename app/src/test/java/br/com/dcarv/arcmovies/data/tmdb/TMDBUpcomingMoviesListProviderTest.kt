package br.com.dcarv.arcmovies.data.tmdb

import br.com.dcarv.arcmovies.data.tmdb.model.TMDBUpcomingMoviesListMapper
import br.com.dcarv.arcmovies.domain.model.Genre
import br.com.dcarv.arcmovies.domain.model.Movie
import br.com.dcarv.arcmovies.domain.model.UpcomingMoviesList
import org.hamcrest.CoreMatchers
import org.junit.Assert
import org.junit.Test
import java.util.*

/**
 * Created by dfcarvalho on 15/01/18.
 */
class TMDBUpcomingMoviesListProviderTest {
    @Test
    fun listUpcomingMovies_success() {
        val provider = TMDBUpcomingMoviesListProvider(
                "MOCK_API_KEY",
                service = MockService(),
                mapper = TMDBUpcomingMoviesListMapper
        )

        val testObserver = provider.listUpcomingMovies().test()

        testObserver.assertComplete()
        testObserver.assertNoErrors()
        testObserver.assertValueCount(1)
        Assert.assertThat(
                testObserver.values(),
                CoreMatchers.hasItems(UpcomingMoviesList(
                        page = 1,
                        results = listOf(Movie(
                                id = 1,
                                title = "Harry Potter e a Pedra Filosofal",
                                overview = "Harry Potter descobre que é um bruxo.",
                                posterPath = "https://image.tmdb.org/t/p/w154/some/path/to/image.png?api_key=1f54bd990f1cdfb230adb312546d765d",
                                backdropPath = "https://image.tmdb.org/t/p/w780/another/path/to/image.png?api_key=1f54bd990f1cdfb230adb312546d765d",
                                genres = listOf(Genre(id = 1, name = "Fantasia")),
                                genreIds = null,
                                releaseDate = GregorianCalendar(2001, 10, 23).time
                        )),
                        maxDate = "2017-01-15",
                        minDate = "2017-03-01",
                        totalPages = 10,
                        totalResults = 300
                ))
        )
    }
}