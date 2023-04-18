package example.exceptions

import kotlinx.coroutines.*

/**
 * our exception is not handled in `coroutineExceptionHandler` and the application crashes!
 * This is because setting `CoroutineExceptionHandler` to child coroutines has no effect.
 *
 * For CoroutineExceptionHandler to work, you need to set it either in CoroutineScope or in top-level coroutines.
 */
fun main() {
    val scope = CoroutineScope(Dispatchers.Default)
    val handler = CoroutineExceptionHandler { _, throwable ->
        println(throwable)
    }

    val job = scope.launch {
        launch(handler) {
            launch {
                throw Exception()
            }
            delay(10)
            println("child1")
        }
    }

    val successfulJob = scope.launch(handler) {
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
        successfulJob.join()
    }
}