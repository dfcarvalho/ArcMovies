package br.com.dcarv.arcmovies.presentation.presenter

import br.com.dcarv.arcmovies.domain.abstraction.IMovieDetailsProvider
import br.com.dcarv.arcmovies.domain.usecase.GetMovieDetailsUseCase
import br.com.dcarv.arcmovies.presentation.view.IMovieDetailsView
import br.com.dcarv.arcmovies.presentation.view.IView

/**
 * Created by dfcarvalho on 17/01/18.
 */
class MovieDetailsPresenter(
        provider: IMovieDetailsProvider,
        private val movieId: Long
) : IMovieDetailsPresenter {
    private var view: IMovieDetailsView? = null
    private val getMovieDetailsUseCase = GetMovieDetailsUseCase(provider, movieId)

    override fun getMovieDetails() {
        disposeObservable()

        getMovieDetailsUseCase.subscribe(
                onNext = { view?.onMovieDetailsReady(it) },
                onError = { view?.onMovieDetailsError(movieId, it) }
        )
    }

    override fun attachView(view: IView) {
        this.view = view as IMovieDetailsView
    }

    override fun detachView() {
        this.view = null
        disposeObservable()
    }

    private fun disposeObservable() {
        if (!getMovieDetailsUseCase.isUnsubscribed()) {
            getMovieDetailsUseCase.unsubscribe()
        }
    }
}