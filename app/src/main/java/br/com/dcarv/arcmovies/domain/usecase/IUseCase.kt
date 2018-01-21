package br.com.dcarv.arcmovies.domain.usecase

import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.disposables.Disposables
import io.reactivex.functions.Action
import io.reactivex.functions.Consumer
import io.reactivex.schedulers.Schedulers

/**
 * Abstract class used as base for all UseCases in app.
 * Children must implement the createObservable() method
 *
 * @author Danilo Carvalho
 */
abstract class IUseCase<T> {
    private var subscription: Disposable = Disposables.empty()

    abstract fun createObservable(): Observable<T>

    fun execute(): Observable<T> {
        return this.createObservable()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
    }

    fun subscribe(
            onNext: (T) -> Unit,
            onError: ((Throwable) -> Unit)? = null,
            onCompleted: (() -> Unit)? = null,
            onSubscribe: ((Disposable) -> Unit)? = null) {
        subscription =
                if (onError != null) {
                    if (onCompleted != null) {
                        if (onSubscribe != null) {
                            this.execute().subscribe(Consumer(onNext), Consumer(onError), Action { onCompleted() }, Consumer(onSubscribe))
                        } else {
                            this.execute().subscribe(onNext, onError, onCompleted)
                        }
                    } else {
                        this.execute().subscribe(onNext, onError)
                    }
                } else {
                    this.execute().subscribe(onNext)
                }
    }

    fun unsubscribe() {
        if (!subscription.isDisposed) {
            subscription.dispose()
        }
    }

    fun isUnsubscribed(): Boolean = subscription.isDisposed
}