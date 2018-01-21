package br.com.dcarv.arcmovies.presentation.presenter

import br.com.dcarv.arcmovies.domain.abstraction.ICountriesListProvider
import br.com.dcarv.arcmovies.domain.abstraction.ILanguagesListProvider
import br.com.dcarv.arcmovies.domain.abstraction.IPreferencesProvider
import br.com.dcarv.arcmovies.domain.abstraction.ITranslationsProvider
import br.com.dcarv.arcmovies.domain.model.Country
import br.com.dcarv.arcmovies.domain.model.Language
import br.com.dcarv.arcmovies.domain.usecase.GetCountriesListUseCase
import br.com.dcarv.arcmovies.domain.usecase.GetLanguagesListUseCase
import br.com.dcarv.arcmovies.presentation.view.IRegionSettingsView
import br.com.dcarv.arcmovies.presentation.view.IView

/**
 * An implementation of IRegionSettingsPresenter for Android
 *
 * @author Danilo Carvalho
 */
class RegionSettingsPresenter(
    private val countriesProvider: ICountriesListProvider,
    private val languagesProvider: ILanguagesListProvider,
    private val translationsProvider: ITranslationsProvider,
    private val preferencesProvider: IPreferencesProvider
) : IRegionSettingsPresenter {
    private var view: IRegionSettingsView? = null
    private val getCountriesListUseCase = GetCountriesListUseCase(countriesProvider)
    private val getLanguagesListUseCase = GetLanguagesListUseCase(languagesProvider, translationsProvider)

    override fun getCountriesList() {
        getCountriesListUseCase.subscribe(
            onNext = { view?.onCountriesListReady(it) },
            onError = { view?.onCountriesListError(it) }
        )
    }

    override fun getLanguagesList() {
        getLanguagesListUseCase.subscribe(
            onNext = { view?.onLanguagesListReady(it) },
            onError = { view?.onLanguagesListError(it) }
        )
    }

    override fun onCountryAndLanguageSelected(country: Country?, language: Language?) {
        country?.let { preferencesProvider.setCountry(it.abbr) }
        language?.let { preferencesProvider.setLanguage(it.translationCode ?: it.code) }

        if (country != null && language != null) {
            view?.onSetSettingsSuccess()
        } else {
            view?.onSetSettingsFailed(country == null, language == null)
        }
    }

    override fun attachView(view: IView) {
        this.view = view as? IRegionSettingsView
    }

    override fun detachView() {
        this.view = null
    }
}