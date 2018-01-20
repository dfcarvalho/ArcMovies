package br.com.dcarv.arcmovies.presentation.presenter

import br.com.dcarv.arcmovies.presentation.view.IView

/**
 * Created by dfcarvalho on 15/01/18.
 */
interface IPresenter {
    fun attachView(view: IView)
    fun detachView()
}