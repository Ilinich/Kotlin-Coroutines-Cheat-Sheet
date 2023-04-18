package example.exceptions

import kotlinx.coroutines.*

fun main() {
    val scope = CoroutineScope(Dispatchers.Default)
    val handler = CoroutineExceptionHandler { _, throwable ->
        println(throwable)
    }

    val job = scope.launch {
        /**
         * we can add CoroutineExceptionHandler to scope or top-level-coroutine
         */
        launch(handler) {
            launch {
                throw RuntimeException("RuntimeException in coroutine")
            }
        }

        delay(100)
        println("coroutine finished")
    }

    runBlocking { job.join() }

    println("work finished")
}

// Exception in thread "DefaultDispatcher-worker-1" java.lang.RuntimeException: RuntimeException in coroutine