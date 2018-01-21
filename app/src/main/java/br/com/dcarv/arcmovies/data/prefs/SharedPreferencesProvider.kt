package br.com.dcarv.arcmovies.data.prefs

import android.content.Context
import br.com.dcarv.arcmovies.domain.abstraction.IPreferencesProvider

/**
 * An IPreferencesProvider that saves preferences in Android's SharedPreferences
 *
 * @author Danilo Carvalho
 */
class SharedPreferencesProvider(context: Context) : IPreferencesProvider {
    private val sharedPreferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)

    override fun getCountry(): String = sharedPreferences.getString(PREF_COUNTRY, "BR")

    override fun setCountry(country: String) {
        val editor = sharedPreferences.edit()
        editor.putString(PREF_COUNTRY, country)
        editor.apply()
    }

    override fun getLanguage(): String = sharedPreferences.getString(PREF_LANGUAGE, "pt-BR")

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