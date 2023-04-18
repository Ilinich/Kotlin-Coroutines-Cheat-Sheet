package example.exceptions

import kotlinx.coroutines.*

/**
 * When an error occurs in a [Coroutine], the error is propagated up the hierarchy.
 *
 * However, when we wrap the falling coroutine in the scope function [coroutineScope], something interesting happens:
 */
fun main() {
    val handler = CoroutineExceptionHandler { _, throwable ->
        println(throwable)
    }
    val scope = CoroutineScope(Dispatchers.Default + handler)

    val job = scope.launch {

        /**
         * the coroutineScope function throws exceptions from child coroutines,
         * instead of propagating through the Job hierarchy. This gives us error handling via try-catch.
         * That we will transform the Coroutine into a call to the suspend function.
         */
        try {
            coroutineScope {
                launch {
                    throw RuntimeException("RuntimeException in nested coroutine")
                }
            }
        } catch (exception: Exception) {
            println("Handle $exception in try/catch")
        }
    }

    runBlocking { job.join() }
}

// Handle java.lang.RuntimeException: RuntimeException in nested coroutine in try/catch