package example.exceptions

import kotlinx.coroutines.*

/**
 * the exception is wrapped in a [Deferred], only for top-level async coroutines.
 * Otherwise, it immediately propagates up the Job hierarchy and is caught by either
 * the [CoroutineExceptionHandler] or passed to an unhandled error handler on the thread, even without calling the [Deferred.await] method
 */
fun main() {
    /**
     * [CoroutineExceptionHandler] not catch up error because exception is wrapped in a Deferred
     *
     *  if we call [Deferred.await] will be exception
     */
    val handler = CoroutineExceptionHandler { _, throwable ->
        println(throwable) // not work for top level async
    }
    val scope = CoroutineScope(Dispatchers.Default)

    /**
     * Top level async does not catch the exception in ExceptionHandler ,
     * this behavior is because Deferred will throw the exception up and when it is told to handle the exception itself,
     * it will not handle it because it does not know how.
     * It will return an error in await
     */
    val job = scope.async(handler) {
        throw RuntimeException("RuntimeException in async coroutine")
    }

    runBlocking { job.join() }

    println("work finished")
}

// work finished