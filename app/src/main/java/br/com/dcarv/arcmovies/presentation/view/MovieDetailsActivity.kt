package br.com.dcarv.arcmovies.presentation.view

import android.os.Build
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.View
import br.com.dcarv.arcmovies.BuildConfig
import br.com.dcarv.arcmovies.R
import br.com.dcarv.arcmovies.TMDB_API_KEY
import br.com.dcarv.arcmovies.TMDB_URL
import br.com.dcarv.arcmovies.data.prefs.SharedPreferencesProvider
import br.com.dcarv.arcmovies.data.tmdb.TMDBMovieDetailsProvider
import br.com.dcarv.arcmovies.data.tmdb.TMDBService
import br.com.dcarv.arcmovies.data.tmdb.model.TMDBMovieMapper
import br.com.dcarv.arcmovies.domain.abstraction.IMovieDetailsProvider
import br.com.dcarv.arcmovies.domain.model.Movie
import br.com.dcarv.arcmovies.presentation.presenter.IMovieDetailsPresenter
import br.com.dcarv.arcmovies.presentation.presenter.MovieDetailsPresenter
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso
import jp.wasabeef.picasso.transformations.CropTransformation
import kotlinx.android.synthetic.main.activity_movie_details.*
import kotlinx.android.synthetic.main.content_movie_details.*
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.text.SimpleDateFormat

/**
 * An implementation of IMovieDetailsActivity for Android.
 * Shows the details for a movie.
 *
 * @author Danilo Carvalho
 */
class MovieDetailsActivity : AppCompatActivity(), IMovieDetailsView {
    private lateinit var service: TMDBService
    private lateinit var presenter: IMovieDetailsPresenter
    private lateinit var moviesProvider: IMovieDetailsProvider
    private var movieId: Long = 0

    private val userDateFormat = SimpleDateFormat.getDateInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movie_details)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        supportPostponeEnterTransition()

        movieId = intent.extras.getLong(EXTRA_MOVIE_ID)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            val imageTransitionName = intent.extras.getString(EXTRA_MOVIE_POSTER_TRANSITION_NAME)
            imgPoster.transitionName = imageTransitionName
        }

        // Load poster image
        val moviePosterUrl = intent.extras.getString(EXTRA_MOVIE_POSTER_URL)
        loadPosterImage(moviePosterUrl, true)

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
        service = retrofit.create(TMDBService::class.java)
        moviesProvider = TMDBMovieDetailsProvider(TMDB_API_KEY, service, mapper = TMDBMovieMapper)
        val language = SharedPreferencesProvider(this.applicationContext).getLanguage()

        presenter = lastCustomNonConfigurationInstance as? IMovieDetailsPresenter ?:
                MovieDetailsPresenter(moviesProvider, movieId, language = language)
        presenter.attachView(this as IView)

        presenter.getMovieDetails()
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.detachView()
    }

    override fun onMovieDetailsReady(movie: Movie) {
        tvTitle.text = movie.title
        tvOverview.text = if (movie.overview.isNullOrBlank()) {
            getString(R.string.label_no_overview)
        } else {
            movie.overview
        }

        val genreName = movie.genres?.getOrNull(0)?.name
        if (genreName != null) {
            tvGenre.text = genreName
            tvGenre.visibility = View.VISIBLE
        } else {
            tvGenre.visibility = View.GONE
        }

        if (movie.releaseDate != null) {
            val strReleaseDate = userDateFormat.format(movie.releaseDate)
            tvRelease.text = strReleaseDate
            tvRelease.visibility = View.VISIBLE
        } else {
            tvRelease.visibility = View.GONE
            tvRelease.text = null
        }

        if (movie.backdropPath != null) {
            Picasso.with(this)
                .load(movie.backdropPath)
                .resize(imgBackdrop.width, 0)
                .transform(
                    CropTransformation(
                        imgBackdrop.width,
                        imgBackdrop.height,
                        CropTransformation.GravityHorizontal.CENTER,
                        CropTransformation.GravityVertical.TOP
                    )
                )
                .transform(VignetteTransformation(VignetteTransformation.TYPE.BOTTOM))
                .into(imgBackdrop, object : Callback {
                    override fun onSuccess() {
                        // do nothing
                        Log.d(TAG, "Image loaded")
                    }

                    override fun onError() {
                        hideCollapsingToolbar()
                    }

                })
        } else {
            hideCollapsingToolbar()
        }
    }

    override fun onMovieDetailsError(movieId: Long, error: Throwable) {
        Log.e(TAG, "Failed to load movie details: " + movieId, error)
        tvTitle.text = getString(R.string.error_failed_movie_details)
    }

    override fun onRetainCustomNonConfigurationInstance(): Any {
        return presenter
    }

    private fun loadPosterImage(posterUrl: String?, startEnterTransition: Boolean = true) {
        Picasso.with(this)
            .load(posterUrl)
            .noFade()
            .placeholder(R.drawable.poster_placeholder)
            .resize(154, 231)
            .centerInside()
            .into(imgPoster, object : Callback {
                override fun onSuccess() {
                    if (startEnterTransition) {
                        supportStartPostponedEnterTransition()
                    }
                }

                override fun onError() {
                    if (startEnterTransition) {
                        supportStartPostponedEnterTransition()
                    }
                }

            })

        // Picasso doesn't execute the callback if posterUrl is null
        if (posterUrl == null) {
            supportStartPostponedEnterTransition()
        }
    }

    private fun hideCollapsingToolbar() {
        imgBackdrop.visibility = View.GONE
        appBarLayout.setExpanded(false, true)
        collapsingToolbar.isTitleEnabled = false
        toolbar.title = getString(R.string.title_activity_movie_details)
    }

    private fun showCollapsingToolbar() {
        imgBackdrop.visibility = View.VISIBLE
        appBarLayout.setExpanded(true, true)
        collapsingToolbar.isTitleEnabled = true
    }

    companion object {
        const val EXTRA_MOVIE_ID = "MOVIE_ID"
        const val EXTRA_MOVIE_POSTER_TRANSITION_NAME = "MOVIE_POSTER_TRANSITION_NAME"
        const val EXTRA_MOVIE_POSTER_URL = "MOVIE_POSTER_URL"

        val TAG = MovieDetailsActivity::class.java.canonicalName!!
    }
}
