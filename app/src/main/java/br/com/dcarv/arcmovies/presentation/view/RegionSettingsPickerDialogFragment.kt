package br.com.dcarv.arcmovies.presentation.view

import android.content.Context
import android.content.DialogInterface
import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import br.com.dcarv.arcmovies.*
import br.com.dcarv.arcmovies.data.prefs.SharedPreferencesProvider
import br.com.dcarv.arcmovies.data.tmdb.TMDBCountryListProvider
import br.com.dcarv.arcmovies.data.tmdb.TMDBLanguagesListProvider
import br.com.dcarv.arcmovies.data.tmdb.TMDBService
import br.com.dcarv.arcmovies.data.tmdb.TMDBTranslationsProvider
import br.com.dcarv.arcmovies.data.tmdb.model.TMDBCountryMapper
import br.com.dcarv.arcmovies.data.tmdb.model.TMDBLanguageMapper
import br.com.dcarv.arcmovies.domain.abstraction.IPreferencesProvider
import br.com.dcarv.arcmovies.domain.model.Country
import br.com.dcarv.arcmovies.domain.model.Language
import br.com.dcarv.arcmovies.presentation.presenter.IRegionSettingsPresenter
import br.com.dcarv.arcmovies.presentation.presenter.RegionSettingsPresenter
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import kotlinx.android.synthetic.main.dialog_region_settings.view.*
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/**
 * An implementation of IRegionSettingsView for Android.
 */
class RegionSettingsPickerDialogFragment : DialogFragment(), IRegionSettingsView,
    AdapterView.OnItemSelectedListener {
    private var selectedCountry: Country? = null
    private var selectedLanguage: Language? = null

    private lateinit var presenter: IRegionSettingsPresenter
    private lateinit var preferencesProvider: IPreferencesProvider

    private var listener: Listener? = null

    override fun onCreateView(
        inflater: LayoutInflater?,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        dialog.setTitle(R.string.title_region_settings)
        return inflater!!.inflate(R.layout.dialog_region_settings, container, false)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // TODO: use Dependency Injection to inject these dependencies instead
        val client = if (BuildConfig.DEBUG) {
            // retrofit logging
            val loggingInterceptor = HttpLoggingInterceptor()
            loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY

            OkHttpClient.Builder()
                .addInterceptor(loggingInterceptor)
                .build()
        } else {
            OkHttpClient.Builder().build()
        }
        val retrofit = Retrofit.Builder()
            .baseUrl(TMDB_URL)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()
        val service = retrofit.create(TMDBService::class.java)
        preferencesProvider = SharedPreferencesProvider(context.applicationContext)

        presenter = RegionSettingsPresenter(
            TMDBCountryListProvider(TMDB_API_KEY, service, mapper = TMDBCountryMapper),
            TMDBLanguagesListProvider(TMDB_API_KEY, service, mapper = TMDBLanguageMapper),
            TMDBTranslationsProvider(TMDB_API_KEY, service),
            preferencesProvider
        )

        presenter.attachView(this)

        view?.spinnerCountry?.isEnabled = false
        view?.progressCountry?.show(true)
        presenter.getCountriesList()


        view?.spinnerLanguage?.isEnabled = false
        view?.progressLanguage?.show(true)
        presenter.getLanguagesList()

        view?.btnOK?.setOnClickListener {
            presenter.onCountryAndLanguageSelected(selectedCountry, selectedLanguage)
        }

        view?.btnCancel?.setOnClickListener {
            dismiss()
        }
    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)

        listener = context as? Listener
    }

    override fun onDismiss(dialog: DialogInterface?) {
        super.onDismiss(dialog)

        presenter.detachView()
    }

    override fun onNothingSelected(p0: AdapterView<*>?) {
        // TODO:
    }

    override fun onItemSelected(adapterView: AdapterView<*>?, view: View?, pos: Int, id: Long) {
        when (adapterView) {
            this.view?.spinnerCountry -> {
                val country = adapterView?.adapter?.getItem(pos) as? Country
                selectedCountry = country
                // remove error (red) from spinner
            }
            this.view?.spinnerLanguage -> {
                val language = adapterView?.adapter?.getItem(pos) as? Language
                selectedLanguage = language
                // remove error (red) from spinner
            }
        }

        this.view?.btnOK?.isEnabled = selectedCountry != null && selectedLanguage != null
    }

    override fun onCountriesListReady(countries: List<Country>) {
        val adapter = ArrayAdapter<Country>(context, R.layout.item_country, R.id.tvName, countries)
        view?.spinnerCountry?.adapter = adapter
        view?.spinnerCountry?.onItemSelectedListener = this
        view?.progressCountry?.show(false)
        view?.spinnerCountry?.isEnabled = true

        val prefsCountry = preferencesProvider.getCountry()
        selectedCountry = countries.find { it.abbr == prefsCountry }
        val pos = countries.indexOf(selectedCountry)

        if (pos >= 0) {
            view?.spinnerCountry?.setSelection(pos)
        }

        view?.btnOK?.isEnabled = selectedCountry != null
    }

    override fun onCountriesListError(error: Throwable) {
        selectedCountry = null
    }

    override fun onLanguagesListReady(languages: List<Language>) {
        val adapter =
            ArrayAdapter<Language>(context, R.layout.item_language, R.id.tvName, languages)
        view?.spinnerLanguage?.adapter = adapter
        view?.spinnerLanguage?.onItemSelectedListener = this
        view?.progressLanguage?.show(false)
        view?.spinnerLanguage?.isEnabled = true

        val prefsLanguage = preferencesProvider.getLanguage()
        selectedLanguage =
                languages.find { it.translationCode == prefsLanguage || it.code == prefsLanguage }
        val pos = languages.indexOf(selectedLanguage)

        if (pos >= 0) {
            view?.spinnerLanguage?.setSelection(pos)
        }

        view?.btnOK?.isEnabled = selectedLanguage != null
    }

    override fun onLanguagesListError(error: Throwable) {
        selectedLanguage = null
    }

    override fun onSetSettingsSuccess() {
        listener?.onSettingsChanged(selectedCountry!!, selectedLanguage!!)
        dismiss()
    }

    override fun onSetSettingsFailed(noCountry: Boolean, noLanguage: Boolean) {
        if (noCountry) {
            // make spinner red
        }

        if (noLanguage) {
            // make spinner red
        }
    }

    companion object {
        fun newInstance(): RegionSettingsPickerDialogFragment {
            return RegionSettingsPickerDialogFragment()
        }
    }

    interface Listener {
        fun onSettingsChanged(country: Country, language: Language)
    }
}