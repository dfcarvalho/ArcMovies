package br.com.dcarv.arcmovies.domain.usecase

import br.com.dcarv.arcmovies.data.tmdb.MockMovieDetailsProvider
import br.com.dcarv.arcmovies.domain.model.Genre
import br.com.dcarv.arcmovies.domain.model.Movie
import org.hamcrest.CoreMatchers
import org.junit.Assert
import org.junit.Test
import java.util.*

/**
 * Created by dfcarvalho on 20/01/18.
 */
class GetMovieDetailsUseCaseTest {
    @Test
    fun testCreateObservable_success() {
        val useCase = GetMovieDetailsUseCase(
            MockMovieDetailsProvider(),
            1
        )

        val testObserver = useCase.createObservable().test()

        testObserver.assertComplete()
        testObserver.assertNoErrors()
        testObserver.assertValueCount(1)
        Assert.assertThat(
            testObserver.values(),
            CoreMatchers.hasItems(
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
        )
    }
}