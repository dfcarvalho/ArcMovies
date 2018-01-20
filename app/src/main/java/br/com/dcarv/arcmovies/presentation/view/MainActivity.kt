package br.com.dcarv.arcmovies.presentation.view

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.support.v4.app.ActivityOptionsCompat
import android.support.v4.view.ViewCompat
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.SearchView
import android.util.Log
import android.view.Menu
import br.com.dcarv.arcmovies.R
import br.com.dcarv.arcmovies.TMDB_API_KEY
import br.com.dcarv.arcmovies.TMDB_URL
import br.com.dcarv.arcmovies.data.tmdb.TMDBGenreProvider
import br.com.dcarv.arcmovies.data.tmdb.TMDBService
import br.com.dcarv.arcmovies.data.tmdb.TMDBUpcomingMoviesListProvider
import br.com.dcarv.arcmovies.data.tmdb.model.TMDBGenreMapper
import br.com.dcarv.arcmovies.data.tmdb.model.TMDBUpcomingMoviesListMapper
import br.com.dcarv.arcmovies.domain.abstraction.IGenreProvider
import br.com.dcarv.arcmovies.domain.abstraction.IUpcomingMoviesListProvider
import br.com.dcarv.arcmovies.domain.model.UpcomingMoviesList
import br.com.dcarv.arcmovies.presentation.presenter.IMainPresenter
import br.com.dcarv.arcmovies.presentation.presenter.MainPresenter
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_main.*
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/**
 * MainActivity
 * Shows the list of upcoming movies
 *
 * @author Danilo Carvalho
 */
class MainActivity : AppCompatActivity(), IMainView, SearchView.OnQueryTextListener {
    // Presenter
    private lateinit var presenter: IMainPresenter

    // Network Dependencies
    private lateinit var service: TMDBService
    private lateinit var moviesProvider: IUpcomingMoviesListProvider
    private lateinit var genreProvider: IGenreProvider

    // Movies Recycler Adapter
    lateinit var adapter: MoviesAdapter

    private var searchView: SearchView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        // TODO: use Dependency Injection to inject these dependencies instead
        // retrofit logging
        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
        val client = OkHttpClient.Builder()
                .addInterceptor(loggingInterceptor)
                .build()
        val retrofit = Retrofit.Builder()
                .baseUrl(TMDB_URL)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build()
        service = retrofit.create(TMDBService::class.java)
        moviesProvider = TMDBUpcomingMoviesListProvider(TMDB_API_KEY, service, mapper = TMDBUpcomingMoviesListMapper)
        genreProvider = TMDBGenreProvider(TMDB_API_KEY, service, mapper = TMDBGenreMapper)

        // Get previous presenter or create new
        presenter = lastCustomNonConfigurationInstance as? IMainPresenter ?: MainPresenter(moviesProvider, genreProvider)
        presenter.attachView(this as IView)

        // setup Recycler View
        val layoutManager = LinearLayoutManager(this)
        rvMovies.layoutManager = layoutManager
        rvMovies.addItemDecoration(DividerItemDecoration(rvMovies.context, layoutManager.orientation))
        adapter = MoviesAdapter(
                presenter.movies,
                onLoadMore = {
                    presenter.getUpcomingMoviesList(presenter.lastLoadedPage + 1)
                    searchView?.setQuery("", true)
                    searchView?.isIconified = true
                    true
                },
                onMovieClicked = { movie, imageView ->

                    val intent = Intent(this, MovieDetailsActivity::class.java)
                    intent.putExtra(MovieDetailsActivity.EXTRA_MOVIE_ID, movie.id)
                    intent.putExtra(MovieDetailsActivity.EXTRA_MOVIE_POSTER_TRANSITION_NAME, ViewCompat.getTransitionName(imageView))
                    intent.putExtra(MovieDetailsActivity.EXTRA_MOVIE_POSTER_URL, movie.posterPath)
                    val options = ActivityOptionsCompat.makeSceneTransitionAnimation(
                            this, imageView, ViewCompat.getTransitionName(imageView))
                    startActivity(intent, options.toBundle())
                },
                loading = presenter.movies.isEmpty(),
                hasMore = presenter.movies.size < presenter.totalMoviesCount
        )
        rvMovies.adapter = adapter
        rvMovies.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView?, dx: Int, dy: Int) {
                // if last visible item is the "Show More" ViewHolder, then start loading more movies
                val pos = layoutManager.findLastVisibleItemPosition()
                if (pos == presenter.movies.size && !adapter.loading) {
                    // avoid illegalstate exception by posting action
                    recyclerView?.post { adapter.showLoading() }
                    // delay a few milliseconds so that user can see progress
                    Handler().postDelayed(
                            { presenter.getUpcomingMoviesList(presenter.lastLoadedPage + 1) },
                            500
                    )

                }
            }
        })

        if (presenter.movies.isEmpty()) presenter.getUpcomingMoviesList()
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.detachView()
    }

    override fun onRetainCustomNonConfigurationInstance(): Any {
        return presenter
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main, menu)

        val searchItem = menu?.findItem(R.id.action_search)
        searchView = searchItem?.actionView as? SearchView
        searchView?.setOnQueryTextListener(this)

        return true
    }

    override fun onUpcomingMoviesListReady(upcomingMoviesList: UpcomingMoviesList) {
        // TODO:
        adapter.addMovies(upcomingMoviesList)
        Log.d("MainActivity", "Loaded page " + upcomingMoviesList.page + " with " + upcomingMoviesList.results.size + " movies.")
    }

    override fun onUpcomingMoviesListError(page: Long, error: Throwable) {
        // TODO:
        adapter.onLoadingError()
        Log.e("MainActivity", "Failed to load upcoming movies list", error)
    }

    override fun onQueryTextChange(newText: String?): Boolean {
        adapter.filter(newText)
        return true
    }

    override fun onQueryTextSubmit(query: String?): Boolean {
        // do nothing
        return false
    }
}
