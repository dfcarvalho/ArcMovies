package br.com.dcarv.arcmovies.presentation.view

import br.com.dcarv.arcmovies.domain.model.Country
import br.com.dcarv.arcmovies.domain.model.Language

/**
 * Created by dfcarvalho on 21/01/18.
 */
interface IRegionSettingsView : IView {
    fun onCountriesListReady(countries: List<Country>)
    fun onCountriesListError(error: Throwable)

    fun onLanguagesListReady(languages: List<Language>)
    fun onLanguagesListError(error: Throwable)

    fun onSetSettingsSuccess()
    fun onSetSettingsFailed(noCountry: Boolean, noLanguage: Boolean)
}