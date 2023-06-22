package example.exceptions

import kotlinx.coroutines.*

/**
 * CoroutineExceptionHandler is an optional element of the CoroutineContext that allows you to handle unhandled exceptions.
 *
 */
fun main() {
    val handler = CoroutineExceptionHandler { coroutineContext, throwable ->
        println(throwable)
    }

    val scope = CoroutineScope(Dispatchers.Default + handler)

    val job = scope // 1
        .launch { // 2
            launch { // 3
                delay(100)
                /**
                 * The [RuntimeException] from the child coroutine propagates up to the [Job] object of the top level coroutine and then further up to the [Job] object in [CoroutineScope].
                 */
                throw RuntimeException("child coroutine exception")
            }
        }

    runBlocking { job.join() }
}

// java.lang.RuntimeException: child coroutine exception