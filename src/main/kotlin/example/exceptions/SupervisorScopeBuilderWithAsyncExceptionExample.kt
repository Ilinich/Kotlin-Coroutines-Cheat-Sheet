package example.exceptions

import kotlinx.coroutines.*

/**
 * When we use the scope function of [supervisorScope], a new, separate, nested scope of type [SupervisorJob] is set up in our Job hierarchy.
 *
 * It is important to understand here that the [supervisorScope] is a new, separate nested scope that should handle exceptions itself.
 *
 * [supervisorScope] does not throw exceptions from its internal coroutines (as [coroutineScope] does),
 * and does not throw exceptions to the parent [Job] (topLevelScope in the example).
 *
 * If the supervisorScope cannot handle the error, then it propagates the exception up the hierarchy.
 */
fun main() {
    val handler = CoroutineExceptionHandler { _, throwable ->
        println("Handler $throwable")
    }
    val scope = CoroutineScope(Dispatchers.Default + handler)

    /**
     * Top-level coroutines behave differently than child coroutines when launched via launch() or async().
     * In addition, CoroutineExceptionHandlers can be set in top-level coroutines.
     */
    val job = scope.async {

        delay(100)
        throw RuntimeException("RuntimeException in nested coroutine")

        println("app top level coroutine finished")
    }

    runBlocking {
        try {
            job.await()
        } catch (e: Throwable) {
            println(e.message)
        }
    }

    runBlocking { job.join() }
}

//catch job1 error=java.lang.RuntimeException: RuntimeException in nested coroutine
//child coroutine 2
//app top level coroutine finished