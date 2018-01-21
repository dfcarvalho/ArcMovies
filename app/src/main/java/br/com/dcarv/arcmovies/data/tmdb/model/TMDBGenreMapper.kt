package br.com.dcarv.arcmovies.data.tmdb.model

import br.com.dcarv.arcmovies.domain.abstraction.IGenreMapper
import br.com.dcarv.arcmovies.domain.model.Genre

/**
 * Maps {@link br.com.dcarv.arcmovies.data.tmdb.model.TMDBGenre} to
 * {@link br.com.dcarv.arcmovies.domain.model.Genre}
 *
 * @author Danilo Carvalho
 */
object TMDBGenreMapper : IGenreMapper<TMDBGenre> {
    override fun toGenre(obj: TMDBGenre) = Genre(
            id = obj.id,
            name = obj.name
    )
}