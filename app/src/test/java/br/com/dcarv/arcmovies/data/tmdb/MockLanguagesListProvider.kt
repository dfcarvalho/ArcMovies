package br.com.dcarv.arcmovies.data.tmdb

import br.com.dcarv.arcmovies.domain.abstraction.ILanguagesListProvider
import br.com.dcarv.arcmovies.domain.model.Language
import io.reactivex.Observable

/**
 * Created by dfcarvalho on 21/01/18.
 */
class MockLanguagesListProvider : ILanguagesListProvider {
    override fun listLanguages(): Observable<List<Language>> {
        return Observable.create { emitter ->
            emitter.onNext(
                listOf(
                    Language(
                        code = "pt-BR",
                        englishName = "Portuguese (Brazil)",
                        name = "Português (Brasil)",
                        translationCode = null
                    ),
                    Language(
                        code = "en-US",
                        englishName = "English (US)",
                        name = "English (US)",
                        translationCode = null
                    )
                )
            )
            emitter.onComplete()
        }
    }
}