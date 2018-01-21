package br.com.dcarv.arcmovies.domain.abstraction


/**
 * @author Danilo Carvalho
 */
interface IPreferencesProvider {
    fun getCountry(): String
    fun setCountry(country: String)

    fun getLanguage(): String
    fun setLanguage(language: String)
}