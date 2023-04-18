package example

import kotlinx.coroutines.*

fun main() {
    val scope = CoroutineScope(Dispatchers.Default)
    val job = scope.launch {

        /**
         * This is a Coroutine context that is not cancelled.
         */
        withContext(NonCancellable) {
            delay(1000)
            println("finished work NonCancellable")
        }
    }
    job.cancel()
    runBlocking {
        job.join()
    }
}

//finished work NonCancellable