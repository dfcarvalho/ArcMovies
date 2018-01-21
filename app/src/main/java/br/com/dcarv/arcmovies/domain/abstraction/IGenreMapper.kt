package br.com.dcarv.arcmovies.domain.abstraction

import br.com.dcarv.arcmovies.domain.model.Genre

/**
 * @author Danilo Carvalho
 */
interface IGenreMapper<in T> {
    fun toGenre(obj: T): Genre
}