package br.com.dcarv.arcmovies.data.tmdb.model

import android.annotation.SuppressLint
import br.com.dcarv.arcmovies.TMDB_API_KEY
import br.com.dcarv.arcmovies.TMDB_BACKDROP_URL
import br.com.dcarv.arcmovies.TMDB_POSTER_URL
import br.com.dcarv.arcmovies.domain.abstraction.IMovieDetailsMapper
import br.com.dcarv.arcmovies.domain.model.Movie
import java.text.ParseException
import java.text.SimpleDateFormat

/**
 * Maps {@link br.com.dcarv.arcmovies.data.tmdb.model.TMDBMovie} to
 * {@link br.com.dcarv.arcmovies.domain.model.Movie}
 *
 * @author Danilo Carvalho
 */
object TMDBMovieMapper : IMovieDetailsMapper<TMDBMovie> {
    @SuppressLint("SimpleDateFormat")
    private val apiDateFormat = SimpleDateFormat("yyyy-MM-dd")

    override fun toMovie(obj: TMDBMovie) = Movie(
            id = obj.id,
            title = obj.title,
            overview = obj.overview,
            posterPath = obj.poster_path?.let { TMDB_POSTER_URL + it + "?api_key=" + TMDB_API_KEY },
            backdropPath = obj.backdrop_path?.let { TMDB_BACKDROP_URL + it + "?api_key=" + TMDB_API_KEY },
            genres = obj.genres,
            genreIds = obj.genre_ids,
            releaseDate = try {
                apiDateFormat.parse(obj.release_date)
            } catch (e: ParseException) {
                null
            }
    )
}