package br.com.dcarv.arcmovies.domain.abstraction

import io.reactivex.Observable

/**
 * @author Danilo Carvalho
 */
interface ITranslationsProvider {
    fun listTranslations(): Observable<List<String>>
}