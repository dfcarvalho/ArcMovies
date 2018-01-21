package br.com.dcarv.arcmovies.presentation.presenter

import br.com.dcarv.arcmovies.domain.model.Country
import br.com.dcarv.arcmovies.domain.model.Language

/**
 * Presenter for the region settings view
 *
 * @author Danilo Carvalho
 */
interface IRegionSettingsPresenter : IPresenter {
    fun getCountriesList()
    fun getLanguagesList()

    fun onCountryAndLanguageSelected(country: Country?, language: Language?)
}