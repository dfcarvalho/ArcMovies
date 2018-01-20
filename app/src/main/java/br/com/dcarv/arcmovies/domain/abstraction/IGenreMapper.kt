package br.com.dcarv.arcmovies.domain.abstraction

import br.com.dcarv.arcmovies.domain.model.Genre

/**
 * Created by dfcarvalho on 16/01/18.
 */
interface IGenreMapper<in T> {
    fun toGenre(obj: T): Genre
}