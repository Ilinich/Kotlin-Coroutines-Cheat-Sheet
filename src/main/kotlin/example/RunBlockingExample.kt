package example

import kotlinx.coroutines.*

fun main() {
    val scope = CoroutineScope(Dispatchers.Default)

    val job = scope.launch {
        delay(100)
        println("coroutine finished work")
    }

    /**
     * runBlocking is a coroutine builder that creates a new coroutine and blocks the thread that called this builder,
     * waiting for execution when the coroutine.
     *
     * runBlocking is a kind of bridge between blocking code and coroutine.
     *
     * The builder creates a special type of coroutine [BlockingCoroutine] which has a [BlockingCoroutine.joinBlocking] method.
     *
     * Method use class [LockSupport] with method [LockSupport.parkNanos]
     *
     * [LockSupport.parkNanos], like [Thread.sleep] ,
     * was created to put the current thread into the
     * [Thread.State.TIMED_WAITING] state.
     * Its advantage, in addition to finer tuning of waiting, is the unique ability to "wake up" a thread using the
     * [LockSupport.unpark] method, as well as the absence of the need to handle an exception.
     *
     */
    runBlocking {
        job.join()
    }
}