package br.com.dcarv.arcmovies.domain.usecase

import br.com.dcarv.arcmovies.domain.abstraction.ILanguagesListProvider
import br.com.dcarv.arcmovies.domain.abstraction.ITranslationsProvider
import br.com.dcarv.arcmovies.domain.model.Language
import io.reactivex.Observable

/**
 * Returns a list of languages sorted by their names
 *
 * @author Danilo Carvalho
 */
class GetLanguagesListUseCase(
    private val provider: ILanguagesListProvider,
    private val translationsProvider: ITranslationsProvider
) : IUseCase<List<Language>>() {
    private var observable: Observable<List<Language>>? = null

    override fun createObservable(): Observable<List<Language>> {
        if (observable == null) {
            // get translations
            observable = translationsProvider.listTranslations()
                .flatMap { translations ->
                    // get languages
                    provider.listLanguages()
                        .map { allLanguages ->
                            // filter out languages without available translations
                            val availableLanguages = allLanguages.map { lang ->
                                lang.translationCode = translations.find { it.contains(lang.code) }
                                lang
                            }.filter { it.translationCode != null }

                            // create list containing languages subset
                            val availableLanguageSubsets = mutableListOf<Language>()
                            availableLanguages.forEach { lang ->
                                val subsets = translations.filter { it.contains(lang.code) }
                                    .map { lang.copy(translationCode = it) }
                                availableLanguageSubsets.addAll(subsets)
                            }
                            availableLanguageSubsets
                        }
                }
//                .map { allLanguages -> allLanguages.filter { it.translationCode != null } }
                .map {
                    // sort
                    it.sortedBy {
                        if (it.name.isBlank()) {
                            it.englishName
                        } else {
                            it.name
                        }
                    }
                }
                .cache()
        }

        return observable!!
    }
}