package br.com.dcarv.arcmovies.data.tmdb

import br.com.dcarv.arcmovies.data.tmdb.model.TMDBGenre
import br.com.dcarv.arcmovies.data.tmdb.model.TMDBGenresList
import br.com.dcarv.arcmovies.data.tmdb.model.TMDBMovie
import br.com.dcarv.arcmovies.data.tmdb.model.TMDBUpcomingResponse
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

/**
 * Created by dfcarvalho on 15/01/18.
 */
interface TMDBService {
    @GET("movie/upcoming")
    fun listUpcomingMovies(
            @Query("api_key") apiKey: String,
            @Query("language") language: String = "pt-BR",
            @Query("page") page: Long = 1,
            @Query("region") region: String = "BR"
    ): Observable<TMDBUpcomingResponse>

    @GET("movie/{id}")
    fun getMovieDetails(
            @Path("id") movieId: Long,
            @Query("api_key") apiKey: String,
            @Query("language") language: String = "pt-BR"
    ): Observable<TMDBMovie>

    @GET("genre/movie/list")
    fun getGenres(
            @Query("api_key") apiKey: String,
            @Query("language") language: String = "pt-BR"
    ): Observable<TMDBGenresList>
}