package br.com.dcarv.arcmovies.domain.abstraction

import br.com.dcarv.arcmovies.domain.model.Language


/**
 * @author Danilo Carvalho
 */
interface ILanguageMapper<in T> {
    fun toLanguage(obj: T): Language
}