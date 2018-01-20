package br.com.dcarv.arcmovies.presentation.view

import android.support.v4.view.ViewCompat
import android.support.v7.widget.AppCompatImageView
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import br.com.dcarv.arcmovies.R
import br.com.dcarv.arcmovies.domain.model.Movie
import br.com.dcarv.arcmovies.domain.model.UpcomingMoviesList
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.item_more.view.*
import kotlinx.android.synthetic.main.item_movie.view.*
import java.text.SimpleDateFormat

/**
 * Movies Adapter
 * Manages ViewHolders for the Upcoming Movies RecyclerVie
 *
 * @author Danilo Carvalho
 */
class MoviesAdapter(
        initialMoviesList: List<Movie> = listOf(),
        private val onLoadMore: (() -> Boolean)? = null,
        private val onMovieClicked: ((Movie, AppCompatImageView?) -> Unit)? = null,
        var loading: Boolean = true,
        private var hasMore: Boolean = true
) : RecyclerView.Adapter<MoviesAdapter.ViewHolder>() {
    // List of movies to be shown
    private val moviesList: MutableList<Movie> = initialMoviesList.toMutableList()
    private val filteredMoviesList: MutableList<Movie> = initialMoviesList.toMutableList()

    // TODO: keep items ordered?
//    private val sortedMoviesList: SortedList<Movie> = SortedList<Movie>(Movie::class.java, object : SortedList.Callback<Movie>() {
//        override fun areItemsTheSame(item1: Movie, item2: Movie) = item1.hashCode() == item2.hashCode()
//
//        override fun areContentsTheSame(oldItem: Movie?, newItem: Movie?) = oldItem == newItem
//
//        override fun compare(o1: Movie, o2: Movie) = o1.releaseDate.compareTo(o2.releaseDate)
//
//        override fun onMoved(fromPosition: Int, toPosition: Int) = notifyItemMoved(fromPosition, toPosition)
//
//        override fun onChanged(position: Int, count: Int) = notifyItemRangeChanged(position, count)
//
//        override fun onInserted(position: Int, count: Int) = notifyItemRangeInserted(position, count)
//
//        override fun onRemoved(position: Int, count: Int) = notifyItemRangeRemoved(position, count)
//    })

    // Date formatter
    private val userDateFormat = SimpleDateFormat.getDateInstance()

    // Filter
    private val movieFilter: Filter = object : Filter() {
        override fun performFiltering(text: CharSequence?): FilterResults {
            val noConstraint = text == null || text.toString().trim().isEmpty()

            val filteredList = moviesList.filter {
                noConstraint || it.title.toLowerCase().contains(
                        text?.toString()?.toLowerCase() ?: "")
            }

            val result = Filter.FilterResults()
            result.count = filteredList.size
            result.values = filteredList

            return result
        }

        override fun publishResults(text: CharSequence?, result: FilterResults) {
            filteredMoviesList.clear()
            @Suppress("UNCHECKED_CAST")
            filteredMoviesList.addAll(result.values as List<Movie>)
            notifyDataSetChanged()
        }

    }

    // TODO: keep items ordered?
//    init {
//        sortedMoviesList.addAll(initialMoviesList)
//    }

    override fun getItemCount() = if (hasMore) filteredMoviesList.size + 1 else filteredMoviesList.size
    // TODO: keep items ordered?
//    override fun getItemCount() = if (hasMore) sortedMoviesList.size() + 1 else sortedMoviesList.size()

    override fun getItemViewType(position: Int) = if (position == filteredMoviesList.size) VH_MORE else VH_MOVIE
    // TODO: keep items ordered?
//    override fun getItemViewType(position: Int) = if (position == sortedMoviesList.size()) VH_MORE else VH_MOVIE

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent?.context)
        return when (viewType) {
            VH_MOVIE -> {
                val view = inflater.inflate(R.layout.item_movie, parent, false)
                MovieViewHolder(view)
            }
            VH_MORE -> {
                val view = inflater.inflate(R.layout.item_more, parent, false)
                MoreViewHolder(view)
            }
            else -> throw RuntimeException("Wrong view type")
        }
    }

    override fun onBindViewHolder(holder: ViewHolder?, position: Int) {
        when (holder) {
            is MovieViewHolder -> bindMovieViewHolder(holder, position)
            is MoreViewHolder -> bindMoreViewHolder(holder)
        }
    }

    private fun bindMovieViewHolder(holder: MovieViewHolder, position: Int) {
        if (position < filteredMoviesList.size) {
            val movie = filteredMoviesList[position]
            // TODO: keep items ordered?
//        if (position < sortedMoviesList.size()) {
//            val movie = sortedMoviesList[position]
            holder.movie = movie

            // Set up poster transition animation
            ViewCompat.setTransitionName(holder.itemView.imgPoster, movie.title)

            // set movie data to views
            loadImage(holder, movie)
            holder.itemView.tvTitle.text = movie.title
            val genreName = movie.genres?.getOrNull(0)?.name
            if (genreName != null) {
                holder.itemView.tvGenre.text = genreName
                holder.itemView.tvGenre.visibility = View.VISIBLE
            } else {
                holder.itemView.tvGenre.visibility = View.GONE
            }

            if (movie.releaseDate != null) {
                val strReleaseDate = userDateFormat.format(movie.releaseDate)
                holder.itemView.tvRelease.text = strReleaseDate
                holder.itemView.tvRelease.visibility = View.VISIBLE
            } else {
                holder.itemView.tvRelease.visibility = View.GONE
                holder.itemView.tvRelease.text = null
            }

            holder.itemView.setOnClickListener {
                onMovieClicked?.invoke(movie, holder.itemView.imgPoster)
            }
        } else {
            // TODO: error
        }
    }

    private fun loadImage(holder: MovieViewHolder, movie: Movie) {
        Picasso.with(holder.itemView.context)
                .load(movie.posterPath)
                .placeholder(R.drawable.poster_placeholder)
                .into(holder.itemView.imgPoster)
    }

    private fun bindMoreViewHolder(holder: MoreViewHolder) {
        if (hasMore && !loading) {
            holder.itemView.tvShowMore.setOnClickListener {
                if (onLoadMore?.invoke() == true) {
                    showLoading()
                }
            }
            holder.itemView.tvShowMore.visibility = View.VISIBLE
        } else {
            holder.itemView.tvShowMore.setOnClickListener(null)
            holder.itemView.tvShowMore.visibility = View.GONE
        }

        holder.itemView.progress.visibility = if (loading) View.VISIBLE else View.GONE
    }

    fun showLoading() {
        loading = true
        notifyItemChanged(filteredMoviesList.size)
        // TODO: keep items ordered?
//        notifyItemChanged(sortedMoviesList.size())
    }

    fun addMovies(upcomingMoviesList: UpcomingMoviesList) {
        val originalSize = moviesList.size
        // TODO: keep items ordered?
//        val originalSize = sortedMoviesList.size()

        moviesList.addAll(upcomingMoviesList.results)
        // TODO: keep items ordered?
//        sortedMoviesList.addAll(upcomingMoviesList.results)

        // TODO: keep items ordered? -> comment next line
        notifyItemRangeInserted(originalSize, upcomingMoviesList.results.size)

        loading = false
        hasMore = upcomingMoviesList.page < upcomingMoviesList.totalPages
        if (!hasMore) {
            notifyItemRemoved(moviesList.size)
            // TODO: keep items ordered?
//            notifyItemRemoved(sortedMoviesList.size())
        } else {
            notifyItemChanged(moviesList.size)
            // TODO: keep items ordered?
//            notifyItemChanged(sortedMoviesList.size())
        }

        filteredMoviesList.clear()
        filteredMoviesList.addAll(moviesList)
    }

    fun onLoadingError() {
        hasMore = false
        loading = false
        notifyItemRemoved(filteredMoviesList.size)
        // TODO: keep items ordered?
//        notifyItemRemoved(sortedMoviesList.size())
    }

    fun filter(text: String?) {
        movieFilter.filter(text)
    }

    abstract class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    class MovieViewHolder(itemView: View) : ViewHolder(itemView) {
        var movie: Movie? = null
    }

    class MoreViewHolder(itemView: View) : ViewHolder(itemView)

    companion object {
        const val VH_MOVIE = 1
        const val VH_MORE = 2
    }
}