package br.com.dcarv.arcmovies.data.tmdb

import br.com.dcarv.arcmovies.data.tmdb.model.TMDBGenreMapper
import br.com.dcarv.arcmovies.domain.model.Genre
import org.hamcrest.CoreMatchers
import org.junit.Assert
import org.junit.Test

/**
 * Created by dfcarvalho on 20/01/18.
 */
class TMDBGenreProviderTest {
    private val service: TMDBService = MockService()

    @Test
    fun listGenres_success() {
        val provider = TMDBGenreProvider(
            "MOCK_API_KEY",
            service = service,
            mapper = TMDBGenreMapper
        )

        val testObserver = provider.listGenres().test()

        testObserver.assertComplete()
        testObserver.assertNoErrors()
        testObserver.assertValueCount(1)
        Assert.assertThat(
            testObserver.values(),
            CoreMatchers.hasItems(
                listOf(
                    Genre(
                        id = 1,
                        name = "Fantasia"
                    ), Genre(
                        id = 2,
                        name = "Policial"
                    ), Genre(
                        id = 3,
                        name = "Terror"
                    )
                )
            )
        )
    }
}