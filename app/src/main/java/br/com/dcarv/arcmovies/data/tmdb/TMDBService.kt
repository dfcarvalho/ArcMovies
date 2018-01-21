package br.com.dcarv.arcmovies.data.tmdb

import br.com.dcarv.arcmovies.data.tmdb.model.*
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

/**
 * Retrofit interface for TMDB API
 *
 * @author Danilo Carvalho
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

    @GET("configuration/countries")
    fun getCountries(
        @Query("api_key") apiKey: String
    ): Observable<List<TMDBCountry>>

    @GET("configuration/languages")
    fun getLanguages(
        @Query("api_key") apiKey: String
    ): Observable<List<TMDBLanguage>>

    @GET("configuration/primary_translations")
    fun getTranslations(
        @Query("api_key") apiKey: String
    ): Observable<List<String >>
}