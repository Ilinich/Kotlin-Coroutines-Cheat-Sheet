package example.exceptions

import kotlinx.coroutines.*
import java.lang.Exception


fun main() {
    val scope = CoroutineScope(Dispatchers.Default)

    val job = scope.launch {
        /**
         * if async not top level coroutine will be exception
         */
        val job = async {
            throw RuntimeException("RuntimeException in async coroutine")
        }
        try {
            job.await()
        } catch (error: Exception) {
            println(error)
        }
        println("coroutine isActive=${isActive}")
        println("coroutine finished")
    }

    runBlocking { job.join() }

    println("work finished")
}


// Exception in thread "DefaultDispatcher-worker-2" java.lang.RuntimeException: RuntimeException in async coroutine