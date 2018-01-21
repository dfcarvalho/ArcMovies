package br.com.dcarv.arcmovies.presentation.presenter

import br.com.dcarv.arcmovies.domain.model.Genre
import br.com.dcarv.arcmovies.domain.model.Movie
import br.com.dcarv.arcmovies.data.tmdb.MockMovieDetailsProvider
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import java.util.*

/**
 * Created by dfcarvalho on 20/01/18.
 */
class MovieDetailsPresenterTest {
    private lateinit var presenter: MovieDetailsPresenter

    @Before
    fun createPresenter() {
        presenter = MovieDetailsPresenter(
            MockMovieDetailsProvider(),
            1L
        )
    }

    @Test
    fun testGetMovieDetails_success() {
        val view = MockMovieDetailsView()
        presenter.attachView(view)

        presenter.getMovieDetails()

        val expected = Movie(
            id = 1,
            title = "Harry Potter e a Pedra Filosofal",
            overview = "Harry Potter descobre que é um bruxo.",
            posterPath = "/some/path/to/image.png",
            backdropPath = "/another/path/to/image.png",
            genres = listOf(Genre(id = 1, name = "Fantasia")),
            genreIds = null,
            releaseDate = GregorianCalendar(2001, 10, 23).time
        )

        assertNotNull(view.movie)
        assertEquals(expected, view.movie)
    }
}