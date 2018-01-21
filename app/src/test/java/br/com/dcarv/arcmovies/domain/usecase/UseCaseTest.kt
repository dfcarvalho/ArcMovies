package br.com.dcarv.arcmovies.domain.usecase

import io.reactivex.Observable
import io.reactivex.Scheduler
import io.reactivex.android.plugins.RxAndroidPlugins
import io.reactivex.disposables.Disposable
import io.reactivex.internal.schedulers.ExecutorScheduler
import io.reactivex.plugins.RxJavaPlugins
import org.hamcrest.CoreMatchers
import org.junit.Assert
import org.junit.Before
import org.junit.BeforeClass
import org.junit.Test
import java.util.concurrent.TimeUnit

/**
 * Created by dfcarvalho on 20/01/18.
 */
class UseCaseTest {
    private lateinit var useCase: IUseCase<String>

    @Before
    fun createUseCase() {
        useCase = object : IUseCase<String>() {
            override fun createObservable(): Observable<String> {
                return Observable.create { emitter ->
                    for (i in 0 until 10) {
                        emitter.onNext("$i")
                    }
                    emitter.onComplete()
                }
            }
        }
    }

    @Test
    fun testCreateObserver_success() {
        val testObserver = useCase.createObservable().test()

        testObserver.assertComplete()
        testObserver.assertNoErrors()
        testObserver.assertValueCount(10)
        Assert.assertThat(
            testObserver.values(),
            CoreMatchers.hasItems(
                "0", "1", "2", "3", "4", "5", "6", "7", "8", "9"
            )
        )
    }

    @Test
    fun testExecute_success() {
        val testObserver = useCase.execute().test()

        testObserver.assertComplete()
        testObserver.assertNoErrors()
        testObserver.assertValueCount(10)
        Assert.assertThat(
            testObserver.values(),
            CoreMatchers.hasItems(
                "0", "1", "2", "3", "4", "5", "6", "7", "8", "9"
            )
        )
    }

    @Test
    fun testSubscribe_success() {
        // TODO:
    }

    companion object {
        @BeforeClass
        @JvmStatic
        fun setup() {
            val immediateScheduler = object : Scheduler() {
                override fun createWorker(): Worker {
                    return ExecutorScheduler.ExecutorWorker(Runnable::run)
                }

                override fun scheduleDirect(
                    run: Runnable,
                    delay: Long,
                    unit: TimeUnit
                ): Disposable {
                    return super.scheduleDirect(run, 0, unit)
                }
            }
            RxJavaPlugins.setInitIoSchedulerHandler { scheduler -> immediateScheduler }
            RxJavaPlugins.setInitComputationSchedulerHandler { scheduler -> immediateScheduler }
            RxJavaPlugins.setInitNewThreadSchedulerHandler  { scheduler -> immediateScheduler }
            RxJavaPlugins.setInitSingleSchedulerHandler  { scheduler -> immediateScheduler }
            RxAndroidPlugins.setInitMainThreadSchedulerHandler  { scheduler -> immediateScheduler }
        }
    }
}