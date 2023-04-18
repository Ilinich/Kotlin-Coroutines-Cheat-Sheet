package example

import kotlinx.coroutines.*

fun main() {
    val scope = CoroutineScope(Dispatchers.Default)

    val job = scope.launch {
        println("finished launch")
    }

    val jobDeferred = scope.async {
        println("finished async")
    }

    runBlocking {
        job.join()

        /**
         * The await method is a suspend function that allows you to wait for the result
         */
        jobDeferred.await()
    }
}