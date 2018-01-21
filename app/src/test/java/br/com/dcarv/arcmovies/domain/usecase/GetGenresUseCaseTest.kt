package br.com.dcarv.arcmovies.domain.usecase

import br.com.dcarv.arcmovies.data.tmdb.MockGenreProvider
import br.com.dcarv.arcmovies.domain.model.Genre
import org.hamcrest.CoreMatchers
import org.junit.Assert
import org.junit.Test

/**
 * Created by dfcarvalho on 20/01/18.
 */
class GetGenresUseCaseTest {
    @Test
    fun testCreateObserver_success() {
        val getGenreUseCase = GetGenresUseCase(MockGenreProvider())

        val testObserver = getGenreUseCase.createObservable().test()

        testObserver.assertComplete()
        testObserver.assertNoErrors()
        testObserver.assertValueCount(1)
        Assert.assertThat(
            testObserver.values(),
            CoreMatchers.`is`(
                listOf(
                    mapOf(
                        Pair(
                            1L, Genre(
                                id = 1,
                                name = "Fantasia"
                            )
                        ), Pair(
                            2L, Genre(
                                id = 2,
                                name = "Policial"
                            )
                        ), Pair(
                            3L, Genre
                                (
                                id = 3,
                                name = "Terror"
                            )
                        )
                    )
                )
            )
        )
    }
}