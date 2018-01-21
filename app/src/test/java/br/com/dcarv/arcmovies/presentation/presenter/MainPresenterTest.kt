package br.com.dcarv.arcmovies.presentation.presenter

import br.com.dcarv.arcmovies.data.tmdb.*
import br.com.dcarv.arcmovies.domain.model.Genre
import br.com.dcarv.arcmovies.domain.model.Movie
import br.com.dcarv.arcmovies.domain.model.UpcomingMoviesList
import io.reactivex.Scheduler
import io.reactivex.android.plugins.RxAndroidPlugins
import io.reactivex.disposables.Disposable
import io.reactivex.internal.schedulers.ExecutorScheduler
import io.reactivex.plugins.RxJavaPlugins
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Before
import org.junit.BeforeClass
import org.junit.Test
import java.util.*
import java.util.concurrent.TimeUnit

/**
 * Created by dfcarvalho on 20/01/18.
 */
class MainPresenterTest {
    private lateinit var presenter: MainPresenter

    @Before
    fun createPresenter() {
        presenter = MainPresenter(
            MockUpcomingMoviesListProvider(),
            MockGenreProvider(),
            MockCountriesProvider(),
            MockLanguagesListProvider(),
            MockTranslationsProvider(),
            MockPreferencesProvider()
        )
    }

    @Test
    fun testGetUpcomingMoviesList() {
        val view = MockMainView()
        presenter.attachView(view)

        presenter.getUpcomingMoviesList(1)

        assertNotNull(view.lastLoadedMoviesList)

        val expectedMovie = Movie(
            id = 1,
            title = "Harry Potter e a Pedra Filosofal",
            overview = "Harry Potter descobre que é um bruxo.",
            posterPath = "/some/path/to/image.png",
            backdropPath = "/another/path/to/image.png",
            genres = listOf(Genre(1, "Fantasia")),
            genreIds = listOf(1L),
            releaseDate = GregorianCalendar(2001, 10, 23).time
        )
        val expectedMovies = listOf(expectedMovie)
        val expectedResult = UpcomingMoviesList(
            page = 1,
            results = listOf(
                Movie(
                    id = 1,
                    title = "Harry Potter e a Pedra Filosofal",
                    overview = "Harry Potter descobre que é um bruxo.",
                    posterPath = "/some/path/to/image.png",
                    backdropPath = "/another/path/to/image.png",
                    genres = listOf(Genre(1, "Fantasia")),
                    genreIds = listOf(1L),
                    releaseDate = GregorianCalendar(2001, 10, 23).time
                )
            ),
            maxDate = "2017-01-15",
            minDate = "2017-03-01",
            totalPages = 10,
            totalResults = 300
        )

        assertEquals(expectedResult, view.lastLoadedMoviesList)
        assertEquals(presenter.totalMoviesCount, 300)
        assertEquals(presenter.movies, expectedMovies)
        assertEquals(presenter.lastLoadedPage, 1)

        presenter.getUpcomingMoviesList(2)
        assertEquals(presenter.movies, expectedMovies + expectedMovie)
    }

    companion object {
        @BeforeClass
        @JvmStatic
        fun setup() {
            val immediateScheduler = object : Scheduler() {
                override fun createWorker(): Worker {
                    return ExecutorScheduler.ExecutorWorker(Runnable::run)
                }

                override fun scheduleDirect(
                    run: Runnable,
                    delay: Long,
                    unit: TimeUnit
                ): Disposable {
                    return super.scheduleDirect(run, 0, unit)
                }
            }
            RxJavaPlugins.setInitIoSchedulerHandler { scheduler -> immediateScheduler }
            RxJavaPlugins.setInitComputationSchedulerHandler { scheduler -> immediateScheduler }
            RxJavaPlugins.setInitNewThreadSchedulerHandler { scheduler -> immediateScheduler }
            RxJavaPlugins.setInitSingleSchedulerHandler { scheduler -> immediateScheduler }
            RxAndroidPlugins.setInitMainThreadSchedulerHandler { scheduler -> immediateScheduler }
        }
    }
}