package br.com.dcarv.arcmovies.domain.usecase

import br.com.dcarv.arcmovies.domain.abstraction.IMovieDetailsProvider
import br.com.dcarv.arcmovies.domain.model.Movie
import io.reactivex.Observable

/**
 * Retrieves the Movie instance with movieId as ID
 *
 * @author Danilo Carvalho
 */
class GetMovieDetailsUseCase(
        private val provider: IMovieDetailsProvider,
        private val movieId: Long,
        private val language: String = "pt-BR"
) : IUseCase<Movie>() {
    override fun createObservable(): Observable<Movie> {
        // TODO: combine with cache
        return provider.getMovieDetails(movieId, language = language)
    }

}