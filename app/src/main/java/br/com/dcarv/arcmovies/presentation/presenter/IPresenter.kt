package br.com.dcarv.arcmovies.presentation.presenter

import br.com.dcarv.arcmovies.presentation.view.IView

/**
 * Base interface for all Presenters
 *
 * @author Danilo Carvalho
 */
interface IPresenter {
    fun attachView(view: IView)
    fun detachView()
}