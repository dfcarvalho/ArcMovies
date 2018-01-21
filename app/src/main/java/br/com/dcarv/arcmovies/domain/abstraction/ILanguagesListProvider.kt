package br.com.dcarv.arcmovies.domain.abstraction

import br.com.dcarv.arcmovies.domain.model.Language
import io.reactivex.Observable

/**
 * @author Danilo Carvalho
 */
interface ILanguagesListProvider {
    fun listLanguages(): Observable<List<Language>>
}