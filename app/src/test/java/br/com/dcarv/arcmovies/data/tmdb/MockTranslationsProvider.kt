package br.com.dcarv.arcmovies.data.tmdb

import br.com.dcarv.arcmovies.domain.abstraction.ITranslationsProvider
import io.reactivex.Observable

/**
 * Created by dfcarvalho on 21/01/18.
 */
class MockTranslationsProvider : ITranslationsProvider {
    override fun listTranslations(): Observable<List<String>> {
        return Observable.create { emitter ->
            emitter.onNext(listOf("pt-BR", "en-US", "de-DE"))
            emitter.onComplete()
        }
    }
}