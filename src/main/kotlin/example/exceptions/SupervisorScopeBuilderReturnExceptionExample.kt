package example.exceptions

import kotlinx.coroutines.*

fun main() {
    val handler = CoroutineExceptionHandler { _, throwable ->
        println("Handler $throwable")
    }
    val scope = CoroutineScope(Dispatchers.Default)

    val job = scope.launch(handler) {

        supervisorScope {

            /**
             * handler is working because it's top-level coroutine
             */
            launch {
                delay(100)
                throw RuntimeException("Exception1 in nested coroutine")
                println("child coroutine 1")
            }

            launch {
                delay(500)
                println("child coroutine 2")
                throw RuntimeException("Exception2 in nested coroutine")
            }
        }

        println("app top level coroutine finished")
    }

    runBlocking { job.join() }
}

//Handler java.lang.RuntimeException: RuntimeException in nested coroutine
//child coroutine 2
// Handler java.lang.RuntimeException: Exception2 in nested coroutine
//app top level coroutine finished