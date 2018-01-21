package br.com.dcarv.arcmovies.domain.usecase

import br.com.dcarv.arcmovies.data.tmdb.MockGenreProvider
import br.com.dcarv.arcmovies.data.tmdb.MockUpcomingMoviesListProvider
import br.com.dcarv.arcmovies.domain.model.Genre
import br.com.dcarv.arcmovies.domain.model.Movie
import br.com.dcarv.arcmovies.domain.model.UpcomingMoviesList
import org.hamcrest.CoreMatchers
import org.junit.Assert
import org.junit.Test
import java.util.*

/**
 * Created by dfcarvalho on 20/01/18.
 */
class GetUpcomingMoviesListUseCaseTest {
    @Test
    fun testCreateObserver_success() {
        val getUpcomingMoviesListUseCase = GetUpcomingMoviesListUseCase(
            provider = MockUpcomingMoviesListProvider(),
            getGenresUseCase = GetGenresUseCase(MockGenreProvider())
        )

        val testObserver = getUpcomingMoviesListUseCase.createObservable().test()

        testObserver.assertComplete()
        testObserver.assertNoErrors()
        testObserver.assertValueCount(1)
        Assert.assertThat(
            testObserver.values(),
            CoreMatchers.hasItems(
                UpcomingMoviesList(
                    page = 1,
                    results = listOf(
                        Movie(
                            id = 1,
                            title = "Harry Potter e a Pedra Filosofal",
                            overview = "Harry Potter descobre que é um bruxo.",
                            posterPath = "/some/path/to/image.png",
                            backdropPath = "/another/path/to/image.png",
                            genres = listOf(Genre(id = 1, name = "Fantasia")),
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
        )
    }
}