package br.com.dcarv.arcmovies.data.prefs

import android.content.Context
import android.os.Build
import br.com.dcarv.arcmovies.domain.abstraction.IPreferencesProvider
import java.util.*

/**
 * An IPreferencesProvider that saves preferences in Android's SharedPreferences
 *
 * @author Danilo Carvalho
 */
class SharedPreferencesProvider(context: Context) : IPreferencesProvider {
    private val sharedPreferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
    private val locale: Locale = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
        context.resources.configuration.locales.get(0)
    } else {
        context.resources.configuration.locale
    }

    override fun getCountry(): String =
        sharedPreferences.getString(PREF_COUNTRY, null) ?: locale.country


    override fun setCountry(country: String) {
        val editor = sharedPreferences.edit()
        editor.putString(PREF_COUNTRY, country)
        editor.apply()
    }

    override fun getLanguage(): String = sharedPreferences.getString(PREF_LANGUAGE, null)
            ?: "${locale.language}-${locale.country}"

    override fun setLanguage(language: String) {
        val editor = sharedPreferences.edit()
        editor.putString(PREF_LANGUAGE, language)
        editor.apply()
    }

    companion object {
        private const val PREFS_NAME = "USER_PREFERENCES"
        private const val PREF_COUNTRY = "COUNTRY"
        private const val PREF_LANGUAGE = "LANGUAGE"
    }
}