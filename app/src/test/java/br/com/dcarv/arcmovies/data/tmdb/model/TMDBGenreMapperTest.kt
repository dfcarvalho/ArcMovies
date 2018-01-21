package br.com.dcarv.arcmovies.data.tmdb.model

import br.com.dcarv.arcmovies.domain.model.Genre
import junit.framework.Assert.assertEquals
import org.junit.Test

/**
 * Created by dfcarvalho on 20/01/18.
 */
class TMDBGenreMapperTest {
    @Test
    fun toGenre_success() {
        val tmdbGenre = TMDBGenre(
            id = 1,
            name = "Fantasia"
        )

        val genre = TMDBGenreMapper.toGenre(tmdbGenre)

        val expected = Genre(
            id = 1,
            name = "Fantasia"
        )

        assertEquals(expected, genre)
    }
}