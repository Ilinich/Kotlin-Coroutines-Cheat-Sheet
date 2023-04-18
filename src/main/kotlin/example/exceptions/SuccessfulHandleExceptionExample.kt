package example.exceptions

import kotlinx.coroutines.*

/**
 * For CoroutineExceptionHandler to work, you need to set it either in CoroutineScope or in top-level coroutines.
 */
fun main() {
    val handler = CoroutineExceptionHandler { _, throwable ->
        println(throwable)
    }

    val scope = CoroutineScope(Dispatchers.Default + handler) // TODO can add to Scope handler

    val job = scope.launch(handler) {
        launch {
            launch {
                throw Exception()
            }
            delay(10)
            println("finished")
        }
    }

    runBlocking {
        job.join()
    }
}

// java.lang.Exception