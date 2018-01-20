package br.com.dcarv.arcmovies.domain.usecase

import br.com.dcarv.arcmovies.domain.abstraction.IGenreProvider
import br.com.dcarv.arcmovies.domain.model.Genre
import io.reactivex.Observable

/**
 * Created by dfcarvalho on 16/01/18.
 */
class GetGenresUseCase(
        private val provider: IGenreProvider,
        val language: String = "pt-BR"
) : IUseCase<Map<Long, Genre>>() {
    private var observable : Observable<Map<Long, Genre>>? = null

    override fun createObservable(): Observable<Map<Long, Genre>> {
        // always return the same cached observer
        if (observable == null) {
            observable = provider.listGenres(language)
                    .map { genresList ->
                        val genresMap = mutableMapOf<Long, Genre>()
                        (0 until genresList.size)
                                .map { genresList[it] }
                                .forEach { genresMap.put(it.id, it) }
                        genresMap as Map<Long, Genre>
                    }
                    .cache()
        }

        return observable!!
    }
}