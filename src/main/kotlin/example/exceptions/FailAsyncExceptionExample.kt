package example.exceptions

import kotlinx.coroutines.*


fun main() {
    val scope = CoroutineScope(Dispatchers.Default)

    val job = scope.launch {
        /**
         * if async not top level coroutin will be exception
         */
        async {
            throw RuntimeException("RuntimeException in async coroutine")
        }
        println("coroutine finished")
    }

    runBlocking { job.join() }

    println("work finished")
}


// Exception in thread "DefaultDispatcher-worker-2" java.lang.RuntimeException: RuntimeException in async coroutine