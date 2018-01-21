package br.com.dcarv.arcmovies.data.tmdb

import br.com.dcarv.arcmovies.domain.abstraction.IGenreProvider
import br.com.dcarv.arcmovies.domain.model.Genre
import io.reactivex.Observable

/**
 * Created by dfcarvalho on 20/01/18.
 */
class MockGenreProvider : IGenreProvider {
    override fun listGenres(language: String): Observable<List<Genre>> {
        return Observable.create { emitter ->
            emitter.onNext(
                listOf(
                    Genre(
                        id = 1,
                        name = "Fantasia"
                    ), Genre(
                        id = 2,
                        name = "Policial"
                    ), Genre
                        (
                        id = 3,
                        name = "Terror"
                    )
                )
            )
            emitter.onComplete()
        }
    }
}