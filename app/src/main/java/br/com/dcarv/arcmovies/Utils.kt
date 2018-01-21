package br.com.dcarv.arcmovies

import android.view.View

/**
 * @author Danilo Carvalho
 */
fun View.show(show: Boolean) {
    this.visibility = if (show) View.VISIBLE else View.GONE
}