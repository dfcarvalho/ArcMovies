package br.com.dcarv.arcmovies.data.tmdb.model

import br.com.dcarv.arcmovies.domain.abstraction.IGenreMapper
import br.com.dcarv.arcmovies.domain.model.Genre

/**
 * Created by dfcarvalho on 16/01/18.
 */
object TMDBGenreMapper : IGenreMapper<TMDBGenre> {
    override fun toGenre(obj: TMDBGenre) = Genre(
            id = obj.id,
            name = obj.name
    )
}