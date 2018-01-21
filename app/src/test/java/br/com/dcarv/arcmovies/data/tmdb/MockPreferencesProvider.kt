package br.com.dcarv.arcmovies.data.tmdb

import br.com.dcarv.arcmovies.domain.abstraction.IPreferencesProvider

/**
 * Created by dfcarvalho on 21/01/18.
 */
class MockPreferencesProvider : IPreferencesProvider {
    private var country: String = "BR"
    private var language: String = "pt-BR"

    override fun getCountry(): String {
        return country
    }

    override fun setCountry(country: String) {
        this.country = country
    }

    override fun getLanguage(): String {
        return language
    }

    override fun setLanguage(language: String) {
        this.language = language
    }
}