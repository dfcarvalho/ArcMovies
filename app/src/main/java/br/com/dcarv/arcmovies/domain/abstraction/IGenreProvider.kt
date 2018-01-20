package br.com.dcarv.arcmovies.domain.abstraction

import br.com.dcarv.arcmovies.domain.model.Genre
import io.reactivex.Observable

/**
 * Created by dfcarvalho on 16/01/18.
 */
interface IGenreProvider {
    fun listGenres(language: String = "pt-BR"): Observable<List<Genre>>
}