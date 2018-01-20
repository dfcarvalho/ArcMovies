package br.com.dcarv.arcmovies.data.tmdb.model

import android.annotation.SuppressLint
import br.com.dcarv.arcmovies.TMDB_API_KEY
import br.com.dcarv.arcmovies.TMDB_BACKDROP_URL
import br.com.dcarv.arcmovies.TMDB_POSTER_URL
import br.com.dcarv.arcmovies.domain.abstraction.IUpcomingMoviesListMapper
import br.com.dcarv.arcmovies.domain.model.Movie
import br.com.dcarv.arcmovies.domain.model.UpcomingMoviesList
import java.text.ParseException
import java.text.SimpleDateFormat

/**
 * Created by dfcarvalho on 15/01/18.
 */
object TMDBUpcomingMoviesListMapper : IUpcomingMoviesListMapper<TMDBUpcomingResponse> {
    @SuppressLint("SimpleDateFormat")
    private val apiDateFormat = SimpleDateFormat("yyyy-MM-dd")

    override fun toUpcomingMoviesList(obj: TMDBUpcomingResponse) = UpcomingMoviesList(
            page = obj.page,
            results = obj.results.map(this::movieMapper),
            maxDate = obj.dates.maximum,
            minDate = obj.dates.minumum,
            totalPages = obj.total_pages,
            totalResults = obj.total_results
    )

    private fun movieMapper(tmdbMovie: TMDBMovie) = Movie(
                id = tmdbMovie.id,
                title = tmdbMovie.title,
                overview = tmdbMovie.overview,
                posterPath = tmdbMovie.poster_path?.let { TMDB_POSTER_URL + it + "?api_key=" + TMDB_API_KEY },
                backdropPath = tmdbMovie.backdrop_path?.let { TMDB_BACKDROP_URL + it + "?api_key=" + TMDB_API_KEY },
                genres = tmdbMovie.genres,
                genreIds = tmdbMovie.genre_ids,
                releaseDate = try {
                    apiDateFormat.parse(tmdbMovie.release_date)
                } catch (e: ParseException) {
                    null
                }
        )
}