package br.com.dcarv.arcmovies.domain.abstraction

import br.com.dcarv.arcmovies.domain.model.Genre
import io.reactivex.Observable

/**
 * @author Danilo Carvalho
 */
interface IGenreProvider {
    fun listGenres(language: String = "pt-BR"): Observable<List<Genre>>
}