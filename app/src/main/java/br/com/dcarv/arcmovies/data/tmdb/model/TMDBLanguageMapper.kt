package br.com.dcarv.arcmovies.data.tmdb.model

import br.com.dcarv.arcmovies.domain.abstraction.ILanguageMapper
import br.com.dcarv.arcmovies.domain.model.Language

/**
 * Maps {@link br.com.dcarv.arcmovies.data.tmdb.model.TMDBLanguage} to
 * {@link br.com.dcarv.arcmovies.domain.model.Language}
 *
 * @author Danilo Carvalho
 */
object TMDBLanguageMapper : ILanguageMapper<TMDBLanguage> {
    override fun toLanguage(obj: TMDBLanguage) = Language(
        code = obj.iso_639_1,
        name = obj.name,
        englishName = obj.english_name,
        translationCode = null
    )
}